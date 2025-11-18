package org.dionisio.task.app.core.di

import org.dionisio.task.app.core.data.database.DatabaseFactory
import org.dionisio.task.app.core.utils.platform.MultiplatformSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseFactory(androidApplication()) }
        single { MultiplatformSettings(context = get()).createSettings() }
    }