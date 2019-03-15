package com.ellington.weatherapp.di.modules

import android.content.Context
import com.ellington.weatherapp.api.api.client.Api
import com.ellington.weatherapp.api.api.client.ApiClient
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
class NetworkingModule {
  @Provides
  fun providesCacheDir(application: Context): File = File(application.cacheDir, "PicassoImageCache")

  @Provides
  fun providesImageCache(file: File): Cache = Cache(file, 1024 * 4)

  @Provides
  fun providesClient(cache: Cache): OkHttpClient = OkHttpClient.Builder().cache(cache).build()

  @Provides
  fun providesGson(): Gson =
    GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create()

  @Provides
  fun providesRetrofit(
    client: OkHttpClient,
    gson: Gson
  ): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    .build()

  @Provides
  fun providesApi(retrofit: Retrofit): ApiClient = ApiClient(retrofit.create(Api::class.java))

  @Provides
  fun providesDownloader(client: OkHttpClient): OkHttp3Downloader = OkHttp3Downloader(client)

  @Provides
  fun providesPicasso(
    application: Context,
    downloader: OkHttp3Downloader
  ): Picasso = Picasso.Builder(application).downloader(downloader).build()
}