package org.dionisio.task

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.dionisio.task.app.data.presentation.theme.TaskTheme
import org.dionisio.task.app.feature.main.MainScreen
import org.dionisio.task.app.feature.main.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun App(
    mainViewModel: MainViewModel = koinViewModel(),
) {
    val darkTheme = mainViewModel.appTheme.collectAsState().value == 1
    val onBoardingCompleted = mainViewModel.onboardingState.collectAsState().value

    TaskTheme(useDarkTheme = darkTheme) {
        val navController = rememberNavController()

        // Actualización de StatusBar y NavigationBar
        //val systemUiController = rememberSystemUiController()
        /*systemUiController.setSystemBarsColor(
            color = MaterialTheme.colorScheme.background
        )
        systemUiController.setNavigationBarColor(
            color = MaterialTheme.colorScheme.background
        )*/

        //if (onBoardingCompleted is OnBoardingState.Success) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            MainScreen(
                navController = navController,
                onBoardingCompleted = true
            )
        }
        //}
    }
}