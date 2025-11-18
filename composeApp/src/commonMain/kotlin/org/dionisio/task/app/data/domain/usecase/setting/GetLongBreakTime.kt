package org.dionisio.task.app.data.domain.usecase.setting

import kotlinx.coroutines.flow.Flow
import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository

class GetLongBreakTime(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Int?> = settingsRepository.getLongBreakTime()
}
