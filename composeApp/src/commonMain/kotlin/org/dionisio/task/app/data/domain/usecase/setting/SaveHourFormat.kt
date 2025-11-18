package org.dionisio.task.app.data.domain.usecase.setting

import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository

class SaveHourFormat(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Int) = settingsRepository.saveHourFormat(value)
}
