package com.ellington.weatherapp.api.api.client

import com.ellington.weatherapp.api.api.models.WeatherDataResponse
import io.reactivex.Single
import retrofit2.adapter.rxjava.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

  @GET("/weather")
  fun getLocationByCityName(
    @Query("q") cityName: String,
    @Query("units") units: String
  ): Single<Result<WeatherDataResponse>>

  @GET("/weather")
  fun getLocationByCoordinates(
    @Query("lat") latitude: Long,
    @Query("long") longitude: Long,
    @Query("units") units: String
  ): Single<Result<WeatherDataResponse>>

  @GET("/weather")
  fun getLocationByZipCode(
    @Query("zip") zipCode: String,
    @Query("units") units: String
  ): Single<Result<WeatherDataResponse>>
}