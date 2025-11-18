package org.dionisio.task.app.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.dionisio.task.app.data.presentation.component.BloomNavigationRailBar
import org.dionisio.task.app.data.presentation.navigation.AppNavHost
import org.dionisio.task.app.data.presentation.navigation.BottomNav
import org.dionisio.task.app.data.presentation.navigation.Destinations
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    onBoardingCompleted: Boolean,
) {
    val windowSizeClass = calculateWindowSizeClass()
    val useNavRail = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact

    if (useNavRail) {
        Row {
            if (onBoardingCompleted) {
                BloomNavigationRailBar(
                    navController = navController,
                )
            }
            AppNavHost(
                navController = navController,
                completedOnboarding = onBoardingCompleted,
            )
        }
    } else {
        Scaffold(
            content = { innerPadding ->
                AppNavHost(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
                    navController = navController,
                    completedOnboarding = onBoardingCompleted,
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .offset(y = 60.dp)
                        .size(42.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navController.navigate(Destinations.AddTask())
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                    ),
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Task",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
                    ?: Destinations.Onboarding::class.qualifiedName.orEmpty()
                val showBottomNavigation =
                    currentRoute in BottomNav.entries.map { it.route::class.qualifiedName }

                val addTask = Destinations.AddTask::class.qualifiedName?.substringBefore("?")

                if (showBottomNavigation || currentRoute == addTask) {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.navigationBarsPadding() // 👈 e
                    ) {
                        BottomNav.entries
                            .forEach { navigationItem ->
                                val isSelected by remember(currentRoute) {
                                    derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                                }
                                BottomNavigationItem(
                                    modifier = Modifier
                                        .testTag(navigationItem.name)
                                        .offset(
                                            x = when (navigationItem.index) {
                                                0 -> 0.dp
                                                1 -> (-24).dp
                                                2 -> 24.dp
                                                3 -> 0.dp
                                                else -> 0.dp
                                            },
                                        ),
                                    selected = isSelected,
                                    label = {
                                        Text(
                                            text = navigationItem.label,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon),
                                            contentDescription = navigationItem.label,
                                            tint = if (isSelected) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.onBackground
                                            },
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(navigationItem.route)
                                    },
                                )
                            }
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MainScreenPreview_Safe() {
    // NavController de preview (no hace navegación real)
    val navController = rememberNavController()

    val currentRoute = Destinations.Home::class.qualifiedName ?: "home"

    // Forzamos "no usar nav rail" en preview (simula un teléfono)
    val useNavRail = false

    // Usamos un Scaffold similar al real pero con contenido de ejemplo
    Scaffold(
        content = { innerPadding ->
            // Contenido de remplazo que normalmente sería AppNavHost
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            ) {
                Text(
                    text = "AppNavHost (preview placeholder)",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            // Botón flotante igual que en producción
            FloatingActionButton(
                modifier = Modifier
                    .offset(y = 60.dp)
                    .size(42.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { /* preview: no navegar */ },
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        bottomBar = {
            // Bottom bar simplificada para preview
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.navigationBarsPadding() // 👈 e
            ) {
                BottomNav.entries
                    .forEach { navigationItem ->
                        val isSelected by remember(currentRoute) {
                            derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                        }
                        BottomNavigationItem(
                            modifier = Modifier
                                .testTag(navigationItem.name)
                                .offset(
                                    x = when (navigationItem.index) {
                                        0 -> 0.dp
                                        1 -> (-24).dp
                                        2 -> 24.dp
                                        3 -> 0.dp
                                        else -> 0.dp
                                    },
                                ),
                            selected = isSelected,
                            label = {
                                Text(
                                    text = navigationItem.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon),
                                    contentDescription = navigationItem.label,
                                    tint = if (isSelected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onBackground
                                    },
                                )
                            },
                            onClick = {
                                navController.navigate(navigationItem.route)
                            },
                        )
                    }
            }
        }
    )
}


