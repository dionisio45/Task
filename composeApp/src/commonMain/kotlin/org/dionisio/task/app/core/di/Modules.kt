package org.dionisio.task.app.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(
        DataFactoryModule,
        SettingsModule,
        TaskModule,
        MainModule
    )
}