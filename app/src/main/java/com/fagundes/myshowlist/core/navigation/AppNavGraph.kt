package com.fagundes.myshowlist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fagundes.myshowlist.components.bottomnavigation.MainScaffold
import com.fagundes.myshowlist.feat.detail.ui.DetailScreen
import com.fagundes.myshowlist.feat.detail.vm.DetailViewModel
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.ui.LoginScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppRoutes.HOME) {
            val viewModel: HomeViewModel = koinViewModel()
            MainScaffold(
                onLogout = {
                    viewModel.logout()
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(0)
                    }
                })
        }

        composable("${AppRoutes.DETAIL}/{type}/{id}") { backStackEntry ->

            val id = backStackEntry.arguments!!.getString("id")!!
            val type = backStackEntry.arguments!!.getString("type")!!

            val viewModel: DetailViewModel = koinViewModel(
                parameters = {
                    parametersOf(id, type)
                }
            )

            DetailScreen(viewModel = viewModel)
        }
    }
}
