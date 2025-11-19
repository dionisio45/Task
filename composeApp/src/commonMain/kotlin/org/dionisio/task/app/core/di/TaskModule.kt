package org.dionisio.task.app.core.di

import org.dionisio.task.app.data.database.repository.task.TasksRepositoryImpl
import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository
import org.dionisio.task.app.data.domain.usecase.task.AddTask
import org.dionisio.task.app.data.domain.usecase.task.DeleteAllTasks
import org.dionisio.task.app.data.domain.usecase.task.DeleteTask
import org.dionisio.task.app.data.domain.usecase.task.GetActiveTask
import org.dionisio.task.app.data.domain.usecase.task.GetTask
import org.dionisio.task.app.data.domain.usecase.task.GetTasks
import org.dionisio.task.app.data.domain.usecase.task.TaskUseCases
import org.dionisio.task.app.data.domain.usecase.task.UpdateAllTasksActiveStatusToInactive
import org.dionisio.task.app.data.domain.usecase.task.UpdateConsumedFocusTime
import org.dionisio.task.app.data.domain.usecase.task.UpdateConsumedLongBreakTime
import org.dionisio.task.app.data.domain.usecase.task.UpdateConsumedShortBreakTime
import org.dionisio.task.app.data.domain.usecase.task.UpdateCurrentSessionName
import org.dionisio.task.app.data.domain.usecase.task.UpdateTaskActive
import org.dionisio.task.app.data.domain.usecase.task.UpdateTaskCompleted
import org.dionisio.task.app.data.domain.usecase.task.UpdateTaskCycleNumber
import org.dionisio.task.app.data.domain.usecase.task.UpdateTaskInProgress
import org.dionisio.task.app.feature.addtask.AddTaskViewModel
import org.dionisio.task.app.feature.home.HomeViewModel
import org.dionisio.task.app.feature.taskprogress.TaskProgressViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val TaskModule  = module{
    singleOf(::TasksRepositoryImpl).bind<TasksRepository>()
    singleOf(::GetTask)
    singleOf(::GetTasks)
    singleOf(::AddTask)
    singleOf(::GetActiveTask)
    singleOf(::DeleteTask)
    singleOf(::DeleteAllTasks)
    singleOf(::UpdateConsumedFocusTime)
    singleOf(::UpdateConsumedShortBreakTime)
    singleOf(::UpdateConsumedLongBreakTime)
    singleOf(::UpdateTaskInProgress)
    singleOf(::UpdateTaskCompleted)
    singleOf(::UpdateCurrentSessionName)
    singleOf(::UpdateTaskCycleNumber)
    singleOf(::UpdateTaskActive)
    singleOf(::UpdateAllTasksActiveStatusToInactive)
    single {
        TaskUseCases(
            addTask = get(),
            getTasks = get(),
            getTask = get(),
            getActiveTask = get(),
            deleteTask = get(),
            deleteAllTasks = get(),
            updateConsumedFocusTime = get(),
            updateConsumedShortBreakTime = get(),
            updateConsumedLongBreakTime = get(),
            updateTaskInProgress = get(),
            updateTaskCompleted = get(),
            updateCurrentSessionName = get(),
            updateTaskCycleNumber = get(),
            updateTaskActive = get(),
            updateAllTasksActiveStatusToInactive = get()
        )
    }
    viewModel {
        AddTaskViewModel(
            taskUseCases = get(),
            settingsUseCases = get()
        )
    }
    viewModel {
        TaskProgressViewModel(
            taskUseCases = get(),
            settingsUseCases = get()
        )
    }
    viewModel {
        HomeViewModel(
            taskUseCases = get(),
            settingsUseCases = get()
        )
    }
}