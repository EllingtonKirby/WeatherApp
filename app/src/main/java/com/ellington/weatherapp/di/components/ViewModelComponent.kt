package com.ellington.weatherapp.di.components

import com.ellington.weatherapp.di.modules.ViewModelBuilder
import dagger.Component


@Component(modules = [ViewModelBuilder::class])
interface ViewModelComponent {

}