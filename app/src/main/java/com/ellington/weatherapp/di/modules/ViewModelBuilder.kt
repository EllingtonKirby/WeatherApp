package com.ellington.weatherapp.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ellington.weatherapp.di.utils.ViewModelKey
import com.ellington.weatherapp.ui.home.locationQuery.LocationQueryViewModel
import com.ellington.weatherapp.ui.mvp.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {

  @Binds
  @IntoMap
  @ViewModelKey(LocationQueryViewModel::class)
  abstract fun bindLocationQueryViewModel(viewModel: LocationQueryViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}