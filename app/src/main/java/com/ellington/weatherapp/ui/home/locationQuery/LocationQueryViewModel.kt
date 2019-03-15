package com.ellington.weatherapp.ui.home.locationQuery

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
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

class LocationQueryViewModel @Inject constructor(
  val apiClient: ApiClient,
  val locationManager: LocationManager
) : BaseViewModel(), LocationListener {
  val toastData: MutableLiveData<String> = MutableLiveData()

  val graphData: MutableLiveData<WeatherGraphData> = MutableLiveData()
  val units: MutableLiveData<WeatherUnits> = MutableLiveData()
  val showLoading: MutableLiveData<Boolean> = MutableLiveData()
  val setQuery: MutableLiveData<String> = MutableLiveData()

  private var fetchSub: Disposable? = null

  fun onQuerySubmitClick(query: String) {
    if (query.isEmpty()) {
      toastData.postValue("Missing query string")
      return
    }
    showLoading.postValue(true)
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
          showLoading.postValue(false)
        },
        {
          toastData.postValue(it.message)
          showLoading.postValue(false)
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
          showLoading.postValue(false)
        },
        {
          toastData.postValue(it.message)
          showLoading.postValue(false)
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
          showLoading.postValue(false)
        },
        {
          toastData.postValue(it.message)
          showLoading.postValue(false)
        }
      )
  }

  fun setMetricSelected() {
    units.postValue(METRIC)
  }

  fun setImperialSelected() {
    units.postValue(IMPERIAL)
  }

  @SuppressLint("MissingPermission")
  fun checkLocation() {
    toastData.postValue("Fetching current location")
    showLoading.postValue(true)

    val providers = locationManager.getProviders(true)
    if (locationManager.getLastKnownLocation(providers.get(0)) != null) {
      val location = locationManager.getLastKnownLocation(providers.get(0))
      setQuery.postValue("${location.latitude},${location.longitude}")
      showLoading.postValue(false)
    } else {
      locationManager.requestSingleUpdate(
        providers.get(0), this, Looper.getMainLooper()
      )
    }
  }

  override fun onLocationChanged(location: Location?) {
    setQuery.postValue("${location?.latitude},${location?.longitude}")
    showLoading.postValue(false)
  }

  override fun onStatusChanged(
    p0: String?,
    p1: Int,
    p2: Bundle?
  ) {
  }

  override fun onProviderEnabled(p0: String?) {
  }

  override fun onProviderDisabled(p0: String?) {
  }
}