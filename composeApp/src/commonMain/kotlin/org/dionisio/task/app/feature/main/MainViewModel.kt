package org.dionisio.task.app.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.dionisio.task.app.data.domain.usecase.setting.SettingsUseCases

class MainViewModel(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val sharingPolicy = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)

    val appTheme: StateFlow<Int> = settingsUseCases
        .getAppTheme()
        .map { it ?: 0 }
        .stateIn(
            scope = viewModelScope,
            started = sharingPolicy,
            initialValue = 0,
        )

   val onboardingState: StateFlow<OnBoardingState> = settingsUseCases
        .getUsername()
        .map { username ->
            if (username.isNullOrBlank()) {
                OnBoardingState.Incomplete
            } else {
                OnBoardingState.Completed
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = sharingPolicy,
            initialValue = OnBoardingState.Loading,
        )
}

sealed interface OnBoardingState {
    data object Loading : OnBoardingState
    data object Incomplete : OnBoardingState
    data object Completed : OnBoardingState
}
