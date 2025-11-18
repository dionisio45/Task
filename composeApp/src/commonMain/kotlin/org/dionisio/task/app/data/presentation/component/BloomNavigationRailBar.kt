package org.dionisio.task.app.data.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.dionisio.task.app.data.presentation.navigation.Destinations
import org.dionisio.task.app.data.presentation.navigation.NavRail
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun BloomNavigationRailBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val windowSizeClass = calculateWindowSizeClass()
    val setWeight = windowSizeClass.heightSizeClass > WindowHeightSizeClass.Compact
    NavigationRail(
        modifier = modifier.fillMaxHeight().alpha(0.95F),
        containerColor = MaterialTheme.colorScheme.surface,
        header = {},
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
            ?: Destinations.Onboarding::class.qualifiedName.orEmpty()

        Column(
            modifier = Modifier.fillMaxHeight()
                .verticalScroll(state = rememberScrollState(), enabled = !setWeight)
        ) {
            NavRail.entries.forEachIndexed { index, navigationItem ->
                val isSelected by remember(currentRoute) {
                    derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                }
                if (setWeight && index == NavRail.entries.size - 1) {
                    Spacer(Modifier.weight(1f))
                }
                NavigationRailItem(
                    modifier = Modifier.padding(vertical = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon),
                            contentDescription = navigationItem.label,
                        )
                    },
                    label = {
                        Text(
                            text = navigationItem.label,
                        )
                    },
                    alwaysShowLabel = true,
                    selected = isSelected,
                    onClick = {
                        navController.navigate(navigationItem.route)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BloomNavigationRailBarPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        BloomNavigationRailBar(
            navController = navController
        )
    }
}
