package ru.riders.sportfinder

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
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
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    private var isFreeAccountMode = false

    @Inject
    lateinit var serverApi: ServerApi

    @Inject
    @Named("SPB_CENTER_POINT")
    lateinit var centerSPbPoint: Point

    val trackPoints = mutableStateListOf<PlacemarkMapObject>()

    var sportsCourts = mutableStateOf(emptyList<SportCourtInfo>())

    var profileName = mutableStateOf("Name Placeholder")

    var tracks = mutableStateOf(emptyList<TrackInfo>())

    var isUserAuthorized = mutableStateOf(false, neverEqualPolicy())
    var userId = 0
        private set
    
    var userToken = ""
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

    fun loadSportCourtsList() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val response = serverApi.getSportSpots().await()
                val result = safeApiResult(response, "Error")
                when (result) {
                    is ApiResult.Success -> {
                        sportsCourts.value = result.data.sportCourts
                            .filter { it.name.isNotEmpty() && it.coordinates.size == 2 }
                            .map { it.toSportCourtInfo() }
                    }
                    is ApiResult.Error -> {}
                }
            } catch (e: Exception) { }
        }
    }

    fun loadUserName() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val response = serverApi.getUserProfile(userId.toString()).await()
                val result = safeApiResult(response, "Error")
                when (result) {
                    is ApiResult.Success -> {
                        profileName.value = result.data.login
                    }
                    is ApiResult.Error -> {}
                }
            } catch (e: Exception) {
                val k = 3
            }
        }
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