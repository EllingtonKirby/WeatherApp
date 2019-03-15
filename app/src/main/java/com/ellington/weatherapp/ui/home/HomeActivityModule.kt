package com.ellington.weatherapp.ui.home

import com.ellington.weatherapp.ui.home.locationQuery.LocationQueryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {

  @ContributesAndroidInjector(modules = [LocationQueryFragmentModule::class])
  internal abstract fun provideLocationQueryFragment(): LocationQueryFragment

  @Module
  abstract class LocationQueryFragmentModule {

  }
}