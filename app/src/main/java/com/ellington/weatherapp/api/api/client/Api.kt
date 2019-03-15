package com.ellington.weatherapp.api.api.client

import com.ellington.weatherapp.api.api.models.WeatherDataResponse
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

  @GET("/data/2.5/weather")
  fun getLocationByCityName(
    @Query("q") cityName: String,
    @Query("units") units: String
  ): Single<Result<WeatherDataResponse>>

  @GET("/data/2.5/weather")
  fun getLocationByCoordinates(
    @Query("lat") latitude: String,
    @Query("long") longitude: String,
    @Query("units") units: String
  ): Single<Result<WeatherDataResponse>>

  @GET("/data/2.5/weather")
  fun getLocationByZipCode(
    @Query("zip") zipCode: String,
    @Query("units") units: String
  ): Single<Result<WeatherDataResponse>>
}