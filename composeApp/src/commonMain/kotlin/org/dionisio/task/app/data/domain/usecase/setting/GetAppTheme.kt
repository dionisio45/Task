package org.dionisio.task.app.data.domain.usecase.setting

import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAppTheme(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Int?> = settingsRepository.getAppTheme()
}
