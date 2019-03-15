package com.ellington.weatherapp.ui.home

import android.os.Bundle
import com.ellington.weatherapp.R
import com.ellington.weatherapp.ui.home.locationQuery.LocationQueryFragment
import com.ellington.weatherapp.ui.mvp.base.BaseActivity

class HomeActivity(override val layoutResourceId: Int = R.layout.activity_home) : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, LocationQueryFragment.newInstance())
        .commit()
    }
  }
}