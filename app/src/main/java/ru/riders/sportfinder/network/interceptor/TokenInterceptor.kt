package ru.riders.sportfinder.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import ru.riders.sportfinder.common.utils.NetworkUtils

class TokenInterceptor: Interceptor {
    private var token: String? = null

    fun updateToken(newToken: String?) {
        token = newToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachToken = invocation
            .method()
            .annotations
            .any { it.annotationClass == WithUserToken::class }

        return if (shouldAttachToken && token != null) {
            Log.d("TokenInterceptor", "Add user Token")
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", NetworkUtils.asBearerHeader(token!!))
                    .build()
            )
        } else {
            chain.proceed(chain.request())
        }
    }
}