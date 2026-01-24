package com.fagundes.myshowlist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fagundes.myshowlist.feat.home.ui.HomeScreen
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.ui.LoginScreen
import org.koin.compose.viewmodel.koinViewModel

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
            HomeScreen(
                onLogout = {
                    viewModel.logout()

                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
