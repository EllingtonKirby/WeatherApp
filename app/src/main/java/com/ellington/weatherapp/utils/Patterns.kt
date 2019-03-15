package com.ellington.weatherapp.utils

import java.util.regex.Pattern

object Patterns {
  val zipCode = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?\$")
  val latLong = Pattern.compile(
    "(^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)\$"
  )
}