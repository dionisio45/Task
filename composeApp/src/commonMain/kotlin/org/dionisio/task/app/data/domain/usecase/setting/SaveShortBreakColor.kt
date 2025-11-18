package org.dionisio.task.app.data.domain.usecase.setting

import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository

class SaveShortBreakColor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Long) = settingsRepository.saveShortBreakColor(value)
}
