package com.ellington.weatherapp.ui.home.locationQuery

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.ellington.weatherapp.api.api.client.ApiClient
import com.ellington.weatherapp.api.api.models.WeatherGraphData
import com.ellington.weatherapp.api.api.models.WeatherUnits
import com.ellington.weatherapp.api.api.models.WeatherUnits.IMPERIAL
import com.ellington.weatherapp.api.api.models.WeatherUnits.METRIC
import com.ellington.weatherapp.ui.mvp.viewmodel.BaseViewModel
import com.ellington.weatherapp.utils.Patterns
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LocationQueryViewModel @Inject constructor(val apiClient: ApiClient) : BaseViewModel() {

  val toastData: MutableLiveData<String> = MutableLiveData()
  val graphData: MutableLiveData<WeatherGraphData> = MutableLiveData()
  val units: MutableLiveData<WeatherUnits> = MutableLiveData()

  private var fetchSub: Disposable? = null

  fun onQuerySubmitClick(query: String) {
    if (query.isEmpty()) {
      toastData.postValue("Missing query string")
      return
    }
    Log.e("Query", query)
    val latLongMatcher = Patterns.latLong.matcher(query)
    val zipCodeMatcher = Patterns.zipCode.matcher(query)
    when {
      latLongMatcher.matches() -> {
        //query by lat long
        val lat = latLongMatcher.group(1)
        val long = latLongMatcher.group(5)
        toastData.postValue(
          "Received lat: $lat, long: $long"
        )
        fetchByLatLong(lat, long)
      }
      zipCodeMatcher.matches() -> {
        //query by zipcode
        val zipCode = zipCodeMatcher.group(0)
        toastData.postValue("Received zipcode: $zipCode")
        fetchByZipCode(zipCode)
      }
      else -> {
        //query by city name
        toastData.postValue("Received city name: $query")
        fetchByCityName(query)
      }
    }
  }

  private fun fetchByLatLong(
    lat: String,
    long: String
  ) {
    fetchSub?.dispose()
    fetchSub = apiClient.getLocationByCoordinates(
      lat, long, units.value?.code ?: "metric"
    )
      .subscribe(
        {
          graphData.postValue(WeatherGraphData.createFromResponse(it))
        },
        {
          toastData.postValue(it.message)
        }
      )
  }

  private fun fetchByZipCode(zipCode: String) {
    fetchSub?.dispose()
    fetchSub = apiClient.getLocationByZipCode(
      zipCode, units.value?.code ?: "metric"
    )
      .subscribe(
        {
          graphData.postValue(WeatherGraphData.createFromResponse(it))
        },
        {
          toastData.postValue(it.message)
        }
      )
  }

  private fun fetchByCityName(cityName: String) {
    fetchSub?.dispose()
    fetchSub = apiClient.getLocationByCityName(
      cityName, units.value?.code ?: "metric"
    )
      .subscribe(
        {
          graphData.postValue(WeatherGraphData.createFromResponse(it))
        },
        {
          toastData.postValue(it.message)
        }
      )
  }

  fun setMetricSelected() {
    units.postValue(METRIC)
  }

  fun setImperialSelected() {
    units.postValue(IMPERIAL)
  }
}