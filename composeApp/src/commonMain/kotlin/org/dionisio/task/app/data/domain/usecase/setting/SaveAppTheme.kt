package org.dionisio.task.app.data.domain.usecase.setting

import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository

class SaveAppTheme(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Int) = settingsRepository.saveAppTheme(value)
}
