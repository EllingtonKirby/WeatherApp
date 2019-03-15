package com.ellington.weatherapp.ui.home.locationQuery

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.getSystemService
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ellington.weatherapp.R
import com.ellington.weatherapp.api.api.models.WeatherUnits
import com.ellington.weatherapp.ui.mvp.viewmodel.ViewModelFragment
import kotlinx.android.synthetic.main.fragment_location_query.location_query_search
import kotlinx.android.synthetic.main.fragment_location_query.search_query_select_imperial
import kotlinx.android.synthetic.main.fragment_location_query.search_query_select_metric
import kotlinx.android.synthetic.main.fragment_location_query.search_query_submit
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

    search_query_submit.setOnClickListener {
      view.let { v ->
        val imm = getSystemService(context ?: return@let, InputMethodManager::class.java)
        imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
      }
      viewModel.onQuerySubmitClick(location_query_search.text.toString())
    }

    search_query_select_metric.setOnClickListener {
      viewModel.setMetricSelected()
    }

    search_query_select_imperial.setOnClickListener {
      viewModel.setImperialSelected()
    }

    search_query_select_metric.callOnClick()
    observeLiveData()
  }

  override fun observeLiveData() {
    viewModel.toastData.observe(this,
      Observer {
        view?.showSnackbar(
          it ?: "",
          Snackbar.LENGTH_LONG,
          {}
        )
      }
    )

    viewModel.units.observe(this,
      Observer {
        when(it) {
          WeatherUnits.IMPERIAL -> {
            search_query_select_imperial.isSelected = true
            search_query_select_metric.isSelected = false
          }
          WeatherUnits.METRIC -> {
            search_query_select_imperial.isSelected = false
            search_query_select_metric.isSelected = true
          }
          null -> return@Observer
        }
      }
    )
  }
}