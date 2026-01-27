package com.fagundes.myshowlist.components.bottomnavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fagundes.myshowlist.core.navigation.AppRoutes
import com.fagundes.myshowlist.feat.catalog.ui.CatalogScreen
import com.fagundes.myshowlist.feat.home.ui.HomeScreen

private val bottomBarRoutes = setOf(
    AppRoutes.HOME,
    AppRoutes.CATALOG
)

@Composable
fun MainScaffold(onLogout: () -> Unit) {
    val navController = rememberNavController()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStack?.destination?.route ?: AppRoutes.HOME

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                AppBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(AppRoutes.HOME)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME,
            modifier = Modifier.padding(padding)
        ) {

            composable(AppRoutes.HOME) {
                HomeScreen(onLogout = onLogout)
            }

            composable(AppRoutes.CATALOG) {
                CatalogScreen()
            }
        }
    }
}
