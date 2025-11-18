package org.dionisio.task.app.data.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.dionisio.task.app.feature.addtask.AddTaskScreenRoot
import org.dionisio.task.app.feature.addtask.AddTaskViewModel
import org.dionisio.task.app.feature.home.AllTasksScreenRoot
import org.dionisio.task.app.feature.home.HomeScreenRoot
import org.dionisio.task.app.feature.home.HomeViewModel
import org.dionisio.task.app.feature.settings.SettingsScreenRoot
import org.dionisio.task.app.feature.settings.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    completedOnboarding: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
         startDestination = if (completedOnboarding) Destinations.Home else Destinations.Onboarding
    ) {
       /* composable<Destinations.Onboarding> {
            OnboardingScreen(
                navController = navController
            )
        }

        composable<Destinations.Username> {
            UsernameScreen(
                navController = navController
            )
        }*/

        composable<Destinations.Home>{
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreenRoot(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable<Destinations.AllTasks> { backStackEntry ->
            val allTasks: Destinations.AllTasks = backStackEntry.toRoute()
            val viewModel = koinViewModel<HomeViewModel>()
             AllTasksScreenRoot(
                 type = allTasks.type,
                 navController = navController,
                 viewModel = viewModel,
             )
        }

        composable<Destinations.AddTask> { backStackEntry ->
            val addTask: Destinations.AddTask = backStackEntry.toRoute()
            val viewModel: AddTaskViewModel = koinViewModel()
            AddTaskScreenRoot(
                taskId = addTask.taskId,
                navController = navController,
                viewModel = viewModel,
            )
        }

       /* composable<Destinations.Calendar> {
            CalendarScreen(navController = navController)
        }

        composable<Destinations.Statistics> {
            StatisticsScreen(navController = navController)
        }

        composable<Destinations.AllStatistics> {
            AllStatisticsScreen(navController = navController)
        }*/

        composable<Destinations.Settings> {
            val viewModel: SettingsViewModel = koinViewModel()
            SettingsScreenRoot(
                navController = navController,
                viewModel = viewModel,
            )
        }

       /* composable<Destinations.TaskProgress> { backStackEntry ->
            val taskProgress: Destinations.TaskProgress = backStackEntry.toRoute()
            TaskProgressScreen(
                taskId = taskProgress.taskId,
                navController = navController
            )
        }*/
    }
}
