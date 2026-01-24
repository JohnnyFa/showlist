package com.fagundes.myshowlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fagundes.myshowlist.core.navigation.AppNavGraph
import com.fagundes.myshowlist.core.navigation.AppRoutes
import com.fagundes.myshowlist.ui.theme.MyShowListTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isUserLoggedIn =
            FirebaseAuth.getInstance().currentUser != null

        setContent {
            MyShowListTheme {
                AppNavGraph(
                    startDestination = if (isUserLoggedIn) {
                        AppRoutes.HOME
                    } else {
                        AppRoutes.LOGIN
                    }
                )
            }
        }
    }
}
