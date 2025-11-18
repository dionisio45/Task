package org.dionisio.task.app.core.di

import org.dionisio.task.app.data.domain.usecase.setting.GetSessionTime
import org.dionisio.task.app.data.database.local.setting.PreferenceManager
import org.dionisio.task.app.data.database.repository.settings.SettingsRepositoryImpl
import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository
import org.dionisio.task.app.data.domain.usecase.setting.ClearAllSettings
import org.dionisio.task.app.data.domain.usecase.setting.GetAppTheme
import org.dionisio.task.app.data.domain.usecase.setting.GetFocusColor
import org.dionisio.task.app.data.domain.usecase.setting.GetHourFormat
import org.dionisio.task.app.data.domain.usecase.setting.GetLongBreakColor
import org.dionisio.task.app.data.domain.usecase.setting.GetLongBreakTime
import org.dionisio.task.app.data.domain.usecase.setting.GetShortBreakColor
import org.dionisio.task.app.data.domain.usecase.setting.GetShortBreakTime
import org.dionisio.task.app.data.domain.usecase.setting.GetUsername
import org.dionisio.task.app.data.domain.usecase.setting.RemindersOn
import org.dionisio.task.app.data.domain.usecase.setting.SaveAppTheme
import org.dionisio.task.app.data.domain.usecase.setting.SaveFocusColor
import org.dionisio.task.app.data.domain.usecase.setting.SaveHourFormat
import org.dionisio.task.app.data.domain.usecase.setting.SaveLongBreakColor
import org.dionisio.task.app.data.domain.usecase.setting.SaveLongBreakTime
import org.dionisio.task.app.data.domain.usecase.setting.SaveSessionTime
import org.dionisio.task.app.data.domain.usecase.setting.SaveShortBreakColor
import org.dionisio.task.app.data.domain.usecase.setting.SaveShortBreakTime
import org.dionisio.task.app.data.domain.usecase.setting.SaveUsername
import org.dionisio.task.app.data.domain.usecase.setting.SettingsUseCases
import org.dionisio.task.app.data.domain.usecase.setting.ToggleReminder
import org.dionisio.task.app.feature.settings.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val SettingsModule = module {
    single { PreferenceManager(get()) }
    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()
    singleOf(::SaveAppTheme)
    singleOf(::GetAppTheme)
    singleOf(::SaveUsername)
    singleOf(::GetUsername)
    singleOf(::SaveSessionTime)
    singleOf(::GetSessionTime)
    singleOf(::SaveShortBreakTime)
    singleOf(::GetShortBreakTime)
    singleOf(::SaveLongBreakTime)
    singleOf(::GetLongBreakTime)
    singleOf(::SaveHourFormat)
    singleOf(::GetHourFormat)
    singleOf(::SaveFocusColor)
    singleOf(::GetFocusColor)
    singleOf(::SaveShortBreakColor)
    singleOf(::GetShortBreakColor)
    singleOf(::SaveLongBreakColor)
    singleOf(::GetLongBreakColor)
    singleOf(::ToggleReminder)
    singleOf(::RemindersOn)
    singleOf(::ClearAllSettings)
    single {
        SettingsUseCases(
            saveAppTheme = get(),
            getAppTheme = get(),
            saveUsername = get(),
            getUsername = get(),
            saveSessionTime = get(),
            getSessionTime = get(),
            saveShortBreakTime = get(),
            getShortBreakTime = get(),
            saveLongBreakTime = get(),
            getLongBreakTime = get(),
            saveHourFormat = get(),
            getHourFormat = get(),
            saveFocusColor = get(),
            getFocusColor = get(),
            saveShortBreakColor = get(),
            getShortBreakColor = get(),
            saveLongBreakColor = get(),
            getLongBreakColor = get(),
            toggleReminder = get(),
            remindersOn = get(),
            clearAll = get(),
        )
    }
    //viewModelOf(::MainViewModel)
    viewModel { SettingsViewModel(get()) }
}