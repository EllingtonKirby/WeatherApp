package com.ellington.weatherapp.api.api.models

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

data class WeatherGraphData(
  val tempData: BarDataSet,
  val pressureData: BarDataSet,
  val humidityData: BarDataSet
) {
  companion object {
    fun createFromResponse(response: WeatherDataResponse): WeatherGraphData? {
      val tempDataSet = mutableListOf<BarEntry>()
      tempDataSet.add(BarEntry(0f, response.main.temp))
      val tempData = BarDataSet(tempDataSet, "")

      val pressureDataSet = mutableListOf<BarEntry>()
      pressureDataSet.add(BarEntry(0f, response.main.pressure))
      val pressureData = BarDataSet(pressureDataSet, "")

      val humidityDataSet = mutableListOf<BarEntry>()
      humidityDataSet.add(BarEntry(0f, response.main.humidity))
      val humidityData = BarDataSet(humidityDataSet, "")

      return WeatherGraphData(tempData, pressureData, humidityData)
    }
  }
}