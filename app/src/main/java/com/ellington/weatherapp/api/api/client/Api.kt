package com.ellington.weatherapp.api.api.client

import retrofit2.http.GET


interface Api {

    @GET() fun get()
}