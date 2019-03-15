package com.ellington.weatherapp.ui.home.locationQuery

import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.ellington.weatherapp.R
import com.ellington.weatherapp.ui.mvp.viewmodel.ViewModelFragment
import javax.inject.Inject

class LocationQueryFragment : ViewModelFragment<LocationQueryViewModel>() {

  @Inject lateinit var sharedPreferences: SharedPreferences

  override val layoutResourceId: Int = R.layout.fragment_location_query

  companion object {
    fun newInstance(): LocationQueryFragment {
      return LocationQueryFragment()
    }
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory)
      .get(LocationQueryViewModel::class.java)
    observeLiveData()
  }

  override fun observeLiveData() {

  }
}
