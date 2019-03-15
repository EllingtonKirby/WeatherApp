package com.ellington.weatherapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.ellington.weatherapp.R
import dagger.Module
import dagger.Provides

@Module
class DataModule {
  @Provides
  fun providesSharedPreferences(application: Context): SharedPreferences =
    application.getSharedPreferences(
      application.getString(R.string.preferences_file_key), Context.MODE_PRIVATE
    )
}