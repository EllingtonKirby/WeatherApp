package com.ellington.weatherapp.api.api.interceptors

import android.util.Log
import com.ellington.weatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
  override fun intercept(chain: Chain): Response {
    var request = chain.request()
    val url = request.url()
      .newBuilder()
      .addQueryParameter("appid", BuildConfig.API_KEY)
      .build()
    request = request.newBuilder()
      .url(url)
      .build()

    val proc = chain.proceed(request)
    Log.v("Request: ", request.url().toString())
    return proc
  }

}