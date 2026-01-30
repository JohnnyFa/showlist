package com.fagundes.myshowlist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fagundes.myshowlist.components.bottomnavigation.MainScaffold
import com.fagundes.myshowlist.feat.catalog.ui.CatalogScreen
import com.fagundes.myshowlist.feat.catalog.vm.CatalogViewModel
import com.fagundes.myshowlist.feat.detail.ui.DetailScreen
import com.fagundes.myshowlist.feat.home.ui.HomeScreen
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.ui.LoginScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavGraph(
    startDestination: String
) {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    rootNavController.navigate(AppRoutes.MAIN) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.MAIN) {

            val homeViewModel: HomeViewModel = koinViewModel()
            val catalogViewModel: CatalogViewModel = koinViewModel()

            val bottomNavController = rememberNavController()

            MainScaffold(
                navController = bottomNavController
            ) {

                NavHost(
                    navController = bottomNavController,
                    startDestination = AppRoutes.HOME
                ) {

                    composable(AppRoutes.HOME) {
                        HomeScreen(
                            navController = rootNavController,
                            onLogout = {
                                homeViewModel.logout()
                                rootNavController.navigate(AppRoutes.LOGIN) {
                                    popUpTo(0)
                                }
                            },
                            viewModel = homeViewModel
                        )
                    }

                    composable(AppRoutes.CATALOG) {
                        CatalogScreen(catalogViewModel)
                    }
                }
            }
        }

        composable("${AppRoutes.DETAIL}/{type}/{id}") { backStackEntry ->

            val id = backStackEntry.arguments!!.getString("id")!!
            val type = backStackEntry.arguments!!.getString("type")!!

            DetailScreen(
                id = id,
                type = type,
                onBack = { rootNavController.popBackStack() }
            )
        }
    }
}
