package org.dionisio.task.app.core.di

import org.dionisio.task.app.feature.home.HomeViewModel
import org.dionisio.task.app.feature.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MainModule = module{
    //viewModelOf(::MainViewModel)
    viewModel {
        MainViewModel(
                 settingsUseCases = get()
        )
    }
}