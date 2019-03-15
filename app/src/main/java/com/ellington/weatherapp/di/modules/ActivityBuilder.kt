package com.ellington.weatherapp.di.modules

import com.ellington.weatherapp.di.utils.PerActivity
import com.ellington.weatherapp.ui.home.HomeActivity
import com.ellington.weatherapp.ui.home.HomeActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

  /**
   * Activities that will be connected to the dependency graph should be indicated in this builder
   *
   * The format as follows:
   *      -@ContributesAndroidInjector: at compile time generate the relevant AndroidInjector
   *      that implements the sub component structure informed by the passed module
   *      -This android injector is then mapped to the return type of this method when the
   *      DispatchingAndroidInjector for the activities is constructed
   *      -After the modules, list providers for the child Fragments of this activity, which
   *      implement a corresponding structure but for Fragments
   */

  @PerActivity
  @ContributesAndroidInjector(
    modules = [
      HomeActivityModule::class
    ]
  )
  abstract fun bindsHomeActivity(): HomeActivity
}