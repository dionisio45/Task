package org.dionisio.task.app.data.domain.usecase.setting

import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository

class SaveUsername(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: String) = settingsRepository.saveUsername(value)
}
