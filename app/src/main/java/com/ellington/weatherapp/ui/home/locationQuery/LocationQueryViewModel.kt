package com.ellington.weatherapp.ui.home.locationQuery

import com.ellington.weatherapp.api.api.client.ApiClient
import com.ellington.weatherapp.ui.mvp.viewmodel.BaseViewModel
import javax.inject.Inject

class LocationQueryViewModel @Inject constructor(val apiClient: ApiClient) : BaseViewModel() {

}