package com.ellington.weatherapp.ui.mvp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ellington.weatherapp.ui.mvp.base.BaseFragment
import javax.inject.Inject

abstract class ViewModelFragment<V : ViewModel> : BaseFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  lateinit var viewModel: V

  abstract fun observeLiveData()
}