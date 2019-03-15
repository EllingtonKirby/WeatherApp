package com.ellington.weatherapp.di.modules

import android.arch.lifecycle.ViewModelProvider
import com.ellington.weatherapp.ui.mvp.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilder {

//    @Binds
//    @IntoMap
//    @ViewModelKey(SquaresFragmentViewModel::class)
//    abstract fun bindSquaresViewModel(viewModel: SquaresFragmentViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SquaresDetailFragmentViewModel::class)
//    abstract fun bindSquaresDetailViewModel(viewModel: SquaresDetailFragmentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}