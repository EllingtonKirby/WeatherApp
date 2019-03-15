package com.ellington.weatherapp.ui.home.locationQuery

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ellington.weatherapp.R
import com.ellington.weatherapp.api.api.models.WeatherUnits
import com.ellington.weatherapp.ui.mvp.viewmodel.ViewModelFragment
import com.ellington.weatherapp.utils.RequestCodeConstance.RC_LOCATION
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
import com.github.mikephil.charting.data.BarData
import kotlinx.android.synthetic.main.fragment_location_query.location_query_search
import kotlinx.android.synthetic.main.fragment_location_query.search_query_humidity_graph
import kotlinx.android.synthetic.main.fragment_location_query.search_query_pressure_graph
import kotlinx.android.synthetic.main.fragment_location_query.search_query_progress
import kotlinx.android.synthetic.main.fragment_location_query.search_query_select_imperial
import kotlinx.android.synthetic.main.fragment_location_query.search_query_select_metric
import kotlinx.android.synthetic.main.fragment_location_query.search_query_submit
import kotlinx.android.synthetic.main.fragment_location_query.search_query_temp_graph

class LocationQueryFragment : ViewModelFragment<LocationQueryViewModel>() {

  override val layoutResourceId: Int = R.layout.fragment_location_query

  companion object {
    const val DEFAULT_TEXT_SIZE: Float = 12f

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

    setupGraphs(search_query_temp_graph)
    setupGraphs(search_query_pressure_graph)
    setupGraphs(search_query_humidity_graph)

    search_query_temp_graph.axisLeft.mAxisMaximum = 125f
    search_query_temp_graph.axisLeft.granularity = .1f
    search_query_temp_graph.description =
      Description().apply { this.text = getString(R.string.temperature_data) }

    search_query_pressure_graph.axisLeft.mAxisMaximum = 2000f
    search_query_pressure_graph.axisLeft.axisMinimum = 1000f
    search_query_pressure_graph.axisLeft.granularity = 10f
    search_query_pressure_graph.description =
      Description().apply { this.text = getString(R.string.pressure_data) }

    search_query_humidity_graph.axisLeft.mAxisMaximum = 100f
    search_query_humidity_graph.axisLeft.axisMinimum = 0f
    search_query_humidity_graph.axisLeft.granularity = .1f
    search_query_humidity_graph.description =
      Description().apply { this.text = getString(R.string.humidity_data) }

    observeLiveData()

    if (ContextCompat.checkSelfPermission(
        context ?: return, android.Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      requestPermissions(
        listOf(android.Manifest.permission.ACCESS_FINE_LOCATION).toTypedArray(),
        RC_LOCATION
      )
    } else {
      viewModel.checkLocation()
    }
  }

  private fun setupGraphs(graph: BarChart) {
    graph.setDrawGridBackground(true)
    graph.setGridBackgroundColor(
      ResourcesCompat.getColor(resources, R.color.primary_material_light, null)
    )

    graph.axisLeft.textSize = DEFAULT_TEXT_SIZE
    graph.axisLeft.typeface = Typeface.DEFAULT_BOLD
    graph.axisLeft.gridColor =
      ResourcesCompat.getColor(resources, R.color.black, null)
    graph.axisRight.isEnabled = false
    graph.axisLeft.isEnabled = true
    graph.xAxis.position = BOTTOM
    graph.xAxis.setDrawGridLines(true)
    graph.axisLeft.setDrawGridLines(true)
    graph.xAxis.granularity = 1f
    graph.xAxis.typeface = Typeface.DEFAULT_BOLD
    graph.legend.isEnabled = false
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    if (requestCode == RC_LOCATION) {
      viewModel.checkLocation()
    }
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
        when (it) {
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

    viewModel.graphData.observe(this,
      Observer {

        val tempSet = it?.tempData
        tempSet?.color = resources.getColor(R.color.colorAccent, null)
        val pressureSet = it?.pressureData
        pressureSet?.color = resources.getColor(R.color.colorAccent, null)
        val humiditySet = it?.humidityData
        humiditySet?.color = resources.getColor(R.color.colorAccent, null)

        search_query_temp_graph.data = BarData(tempSet).apply { barWidth = .9f }
        search_query_pressure_graph.data = BarData(pressureSet).apply { barWidth = .9f }
        search_query_humidity_graph.data = BarData(humiditySet).apply { barWidth = .9f }

        search_query_temp_graph.callOnClick()
        search_query_pressure_graph.callOnClick()
        search_query_humidity_graph.callOnClick()
      }
    )

    viewModel.showLoading.observe(this, Observer {
      if (it == true) {
        search_query_progress.visibility = View.VISIBLE
      } else {
        search_query_progress.visibility = View.GONE
      }
    })

    viewModel.setQuery.observe(this, Observer {
      location_query_search.setText(it ?: "")
    })
  }
}