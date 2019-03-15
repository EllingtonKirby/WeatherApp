package com.ellington.weatherapp.app

import android.app.Activity
import android.support.v4.app.Fragment
import com.ellington.weatherapp.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class App : DaggerApplication(), HasActivityInjector, HasSupportFragmentInjector {
  /**
   * Wrappers around mappings of classes descended from android components to relevant injectors
   * Mapping is assembled via @ContributesAndroidInjector annotated methods
   */
  @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
    return activityInjector
  }

  override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
    return fragmentInjector
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
    return DaggerAppComponent
      .builder()
      .application(this)
      .build()
  }
}