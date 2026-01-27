package com.fagundes.myshowlist.feat.login.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.fagundes.myshowlist.feat.login.vm.LoginUiState
import com.fagundes.myshowlist.feat.login.vm.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.fagundes.myshowlist.R
import com.fagundes.myshowlist.feat.login.vm.LoginUiEvent
import org.koin.compose.viewmodel.koinViewModel
import android.app.Activity
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import com.fagundes.myshowlist.components.AppText
import com.fagundes.myshowlist.components.CaptionText
import com.fagundes.myshowlist.components.ErrorText
import com.fagundes.myshowlist.components.SubtitleText
import com.fagundes.myshowlist.components.TitleText

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val webClientId = stringResource(R.string.default_web_client_id)

    val googleSignInClient = remember(webClientId) {
        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        GoogleSignIn.getClient(context, gso)
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let {
                    viewModel.onGoogleTokenReceived(it)
                }
            } catch (_: ApiException) {
                println("ApiException tratar depois")
            }
        }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            if (event is LoginUiEvent.NavigateHome) {
                onLoginSuccess()
            }
        }
    }

    LoginScreenContent(
        state = state,
        onLoginClick = {
            launcher.launch(googleSignInClient.signInIntent)
        }
    )
}

@Composable
fun GoogleLoginButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .shadow(6.dp, RoundedCornerShape(14.dp))
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = Color.Black
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(Modifier.width(12.dp))
                AppText(
                    text = stringResource(R.string.continue_with_google),
                    weight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(name = "Loading")
@Composable
fun LoginLoadingPreview() {
    LoginScreenContent(
        state = LoginUiState.Loading,
        onLoginClick = {}
    )
}

@Preview(name = "Error")
@Composable
fun LoginErrorPreview() {
    LoginScreenContent(
        state = LoginUiState.Error(stringResource(R.string.authentication_failed)),
        onLoginClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        state = LoginUiState.Idle,
        onLoginClick = {}
    )
}


@Composable
fun LoginScreenContent(
    state: LoginUiState,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B0B0F),
                        Color(0xFF1C1C2E)
                    )
                )
            )
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(84.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE50914),
                                Color(0xFF9C27B0)
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            TitleText(
                text = stringResource(R.string.app_name)
            )

            Spacer(Modifier.height(8.dp))

            SubtitleText(
                text = stringResource(R.string.your_premium_cinema_experience)
            )

            Spacer(Modifier.height(48.dp))

            GoogleLoginButton(
                isLoading = state is LoginUiState.Loading,
                onClick = onLoginClick
            )

            Spacer(Modifier.height(16.dp))

            CaptionText(
                text = stringResource(R.string.terms_and_privacy_policy_full)
            )

            if (state is LoginUiState.Error) {
                Spacer(Modifier.height(16.dp))
                ErrorText(text = state.message)
            }
        }
    }
}