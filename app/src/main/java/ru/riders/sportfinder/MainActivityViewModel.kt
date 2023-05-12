package ru.riders.sportfinder

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.riders.sportfinder.data.ApiResult
import ru.riders.sportfinder.data.SignUpRequestBody
import ru.riders.sportfinder.di.api.ServerApi
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var serverApi: ServerApi

    var isUserAuthorized = mutableStateOf(false, neverEqualPolicy())
    private var userId = 0
        get() {
            return if (isUserAuthorized.value) field
            else throw IllegalStateException("User haven`t authorized")
        }
    private var userToken = ""
        get() {
            return if (isUserAuthorized.value) field
            else throw IllegalStateException("User haven`t authorized")
        }


    fun trySignUp(
        login: String,
        password: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
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


    fun <T : Any> safeApiResult(response: Response<T>, errorMessage: String): ApiResult<T> {
        if (response.isSuccessful) return ApiResult.Success(response.body()!!)

        return ApiResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

}