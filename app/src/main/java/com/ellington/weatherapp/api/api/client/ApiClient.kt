package com.ellington.weatherapp.api.api.client

import com.ellington.weatherapp.api.api.models.WeatherDataResponse
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.Result

class ApiClient(val api: Api) {

  fun getLocationByCityName(
    cityName: String,
    units: String
  ): Single<WeatherDataResponse> {
    return api.getLocationByCityName(cityName, units)
      .compose(getDefaultTransformer())
  }

  fun getLocationByCoordinates(
    latitude: String,
    longitude: String,
    units: String
  ): Single<WeatherDataResponse> {
    return api.getLocationByCoordinates(latitude, longitude, units)
      .compose(getDefaultTransformer())
  }

  fun getLocationByZipCode(
    zipCode: String,
    units: String
  ): Single<WeatherDataResponse> {
    return api.getLocationByZipCode(zipCode, units)
      .compose(getDefaultTransformer())
  }

  private fun <T> getDefaultTransformer(): SingleTransformer<Result<T>, T?> {
    return SingleTransformer {
      return@SingleTransformer it
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { result ->
          when {
            result.isError -> return@flatMap Single.error<T>(result.error())
            result.response()?.errorBody() != null -> return@flatMap Single.error<T>(
              Throwable(result.response()?.errorBody()?.string())
            )
            else -> return@flatMap Single.just<T>(
              result.response()?.body()
            )
          }
        }
    }
  }
}