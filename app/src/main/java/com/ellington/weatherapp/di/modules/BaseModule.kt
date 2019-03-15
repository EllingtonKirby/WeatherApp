package com.ellington.weatherapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module


@Module
abstract class BaseModule {
    @Binds
    internal abstract fun provideContext(application: Application): Context
}