package com.ellington.weatherapp.api.api.models

import com.github.mikephil.charting.data.BarDataSet

data class WeatherGraphData(
  val tempData: BarDataSet,
  val pressureData: BarDataSet,
  val humidityData: BarDataSet
) {
  companion object {
    fun createFromResponse(response: WeatherDataResponse): WeatherGraphData? {
      return null
    }
  }
}