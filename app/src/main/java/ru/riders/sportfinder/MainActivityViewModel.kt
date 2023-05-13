package ru.riders.sportfinder

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.riders.sportfinder.data.SportCourtInfo
import ru.riders.sportfinder.data.TrackInfo
import ru.riders.sportfinder.data.networkData.ApiResult
import ru.riders.sportfinder.data.networkData.SignUpRequestBody
import ru.riders.sportfinder.di.api.ServerApi
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    private var isFreeAccountMode = true

    @Inject
    lateinit var serverApi: ServerApi

    var sportsCourts = mutableStateOf(emptyList<SportCourtInfo>())

    var tracks = mutableStateOf(emptyList<TrackInfo>())

    var isUserAuthorized = mutableStateOf(false, neverEqualPolicy())
    var userId = 0
        get() {
            return if (isUserAuthorized.value) field
            else 0 /*throw IllegalStateException("User haven`t authorized")*/
        }
        private set
    
    var userToken = ""
        get() {
            return if (isUserAuthorized.value) field
            else "" /*throw IllegalStateException("User haven`t authorized")*/
        }
        private set


    fun trySignUp(
        login: String,
        password: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                // FIXME: Запрос без авторизации, для удобства тестирования
                if (isFreeAccountMode) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                    return@launch
                }

                val response = serverApi.signUp(SignUpRequestBody(login, password)).await()
                val result = safeApiResult(response, "Error")
                when (result) {
                    is ApiResult.Success -> {
                        isUserAuthorized.value = true
                        userId = result.data.id
                        userToken = result.data.token
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }
                    is ApiResult.Error -> {
                        isUserAuthorized.value = false
                        withContext(Dispatchers.Main) {
                            onFailed()
                        }
                    }
                }
            } catch (e: Exception) {
                isUserAuthorized.value = false
                withContext(Dispatchers.Main) {
                    onFailed()
                }
            }
        }
    }

    fun trySignIn(
        login: String,
        password: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                // FIXME: Запрос без авторизации, для удобства тестирования
                if (isFreeAccountMode) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                    return@launch
                }

                val response = serverApi.signIn(login, password).await()
                val result = safeApiResult(response, "Error")
                when (result) {
                    is ApiResult.Success -> {
                        isUserAuthorized.value = true
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }
                    is ApiResult.Error -> {
                        isUserAuthorized.value = false
                        withContext(Dispatchers.Main) {
                            onFailed()
                        }
                    }
                }
            } catch (e: Exception) {
                isUserAuthorized.value = false
                withContext(Dispatchers.Main) {
                    onFailed()
                }
            }
        }
    }

    fun loadSportCourtsListMock() {
        // TODO: Заменить на загрузку данных
        sportsCourts.value = listOf(
            SportCourtInfo(
                name = "Девяткино",
                tags = listOf("одна дорога на Лаврики", "дыра"),
                distance = 999.9F,
                temperature = -666.6F,
                courtId = 1,
                coordinates = Point()
            ),
            SportCourtInfo(
                name = "Старая Деревня",
                tags = listOf("уют", "шава"),
                distance = 1.3F,
                temperature = 24.6F,
                courtId = 1,
                coordinates = Point()
            ),
            SportCourtInfo(
                name = "Новая",
                tags = listOf("новая", "без шавы"),
                distance = 0.3F,
                temperature = 21.2F,
                courtId = 1,
                coordinates = Point()
            ),
        )
    }

    fun loadTrackListMock() {
        // TODO: Заменить на загрузку данных
        tracks.value = mutableListOf<TrackInfo>().apply {
            repeat(10) { i ->
                add(TrackInfo(
                    "Title $i",
                    i*100.toDouble(),
                    i+10,
                    listOf("tag ${i+1}", "tag ${i+2}", "tag ${i+3}", "tag ${i+4}"),
                    listOf(Point(59.991576, 30.319135),
                        Point(59.991015, 30.321215),
                        Point(59.989240, 30.322062),
                        Point(59.985143, 30.319069)),
                    -i,
                    i
                ))
            }
        }
    }


    fun <T : Any> safeApiResult(response: Response<T>, errorMessage: String): ApiResult<T> {
        if (response.isSuccessful) return ApiResult.Success(response.body()!!)

        return ApiResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

}