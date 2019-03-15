package com.ellington.weatherapp.api.api.models

data class WeatherDataResponse(
  val main: TemperatureData
)

data class TemperatureData(
  val temp: Double,
  val pressure: Double,
  val humidity: Double,
  val tempMin: Double,
  val tempMax: Double
)