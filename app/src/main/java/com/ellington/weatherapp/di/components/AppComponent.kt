package com.ellington.weatherapp.di.components

import android.app.Application
import com.ellington.weatherapp.app.App
import com.ellington.weatherapp.di.modules.ActivityBuilder
import com.ellington.weatherapp.di.modules.BaseModule
import com.ellington.weatherapp.di.modules.DataModule
import com.ellington.weatherapp.di.modules.NetworkingModule
import com.ellington.weatherapp.di.modules.ViewModelBuilder
import com.ellington.weatherapp.di.utils.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    BaseModule::class,
    NetworkingModule::class,
    DataModule::class,
    ViewModelBuilder::class,
    ActivityBuilder::class
  ]
)

interface AppComponent : AndroidInjector<App> {

  @Component.Builder
  interface Builder {
    @BindsInstance fun application(application: Application): Builder
    fun build(): AppComponent
  }

  fun inject(app: Application)
}