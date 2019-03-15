package com.ellington.weatherapp.di.modules

import android.content.Context
import android.location.LocationManager
import com.ellington.weatherapp.api.api.client.Api
import com.ellington.weatherapp.api.api.client.ApiClient
import com.ellington.weatherapp.api.api.interceptors.ApiKeyInterceptor
import com.ellington.weatherapp.di.utils.PerApplication
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
class NetworkingModule {
  @PerApplication
  @Provides
  fun providesCacheDir(application: Context): File = File(application.cacheDir, "PicassoImageCache")

  @PerApplication
  @Provides
  fun providesImageCache(file: File): Cache = Cache(file, 1024 * 4)

  @PerApplication
  @Provides
  fun providesClient(cache: Cache): OkHttpClient =
    OkHttpClient.Builder().cache(cache).addInterceptor(ApiKeyInterceptor()).build()

  @PerApplication
  @Provides
  fun providesGson(): Gson =
    GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create()

  @PerApplication
  @Provides
  fun providesRetrofit(
    client: OkHttpClient,
    gson: Gson
  ): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

  @PerApplication
  @Provides
  fun providesApi(retrofit: Retrofit): ApiClient = ApiClient(retrofit.create(Api::class.java))

  @PerApplication
  @Provides
  fun providesLocationManager(context: Context): LocationManager =
    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}