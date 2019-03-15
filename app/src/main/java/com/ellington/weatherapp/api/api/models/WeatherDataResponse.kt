package com.ellington.weatherapp.api.api.models

data class WeatherDataResponse(
  val main: TemperatureData
)

data class TemperatureData(
  val temp: Float,
  val pressure: Float,
  val humidity: Float,
  val tempMin: Float,
  val tempMax: Float
)