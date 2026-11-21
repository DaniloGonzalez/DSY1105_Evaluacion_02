package com.example.ev2_master_martini.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class NetworkLogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val start = System.currentTimeMillis()

        val response = chain.proceed(request)

        val duration = System.currentTimeMillis() - start
        NetworkLogStore.add(
            NetworkCall(
                method = request.method,
                url = request.url.toString(),
                code = response.code,
                durationMs = duration,
                timestamp = System.currentTimeMillis()
            )
        )

        return response
    }
}
