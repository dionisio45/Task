package org.dionisio.task.app.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.dionisio.task.app.core.data.database.DatabaseFactory
import org.dionisio.task.app.core.data.database.TaskDatabase
import org.koin.dsl.module

val DataFactoryModule  = module{
    single { get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<TaskDatabase>().taskDao }
}