package com.fagundes.myshowlist.components.bottomnavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fagundes.myshowlist.core.navigation.AppRoutes

private val bottomBarRoutes = setOf(
    AppRoutes.HOME,
    AppRoutes.CATALOG
)

@Composable
fun MainScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit
) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                AppBottomNavigation(
                    currentRoute = currentRoute ?: AppRoutes.HOME,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(AppRoutes.HOME) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            content()
        }
    }
}
