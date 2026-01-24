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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // 🔑 WEB CLIENT ID (Firebase)
    val webClientId = stringResource(R.string.default_web_client_id)
    println("WEB CLIENT ID = $webClientId")

    // ✅ Google Sign-In Client
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
                val idToken = account.idToken

                if (idToken == null) {
                    println("❌ ID TOKEN NULL — verifique Web Client ID / SHA-1")
                    return@rememberLauncherForActivityResult
                }

                viewModel.onGoogleTokenReceived(idToken)

            } catch (e: ApiException) {
                println("❌ Erro Google Sign-In: ${e.statusCode}")
            }
        }

    // ✅ One-shot navigation event
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.NavigateHome -> onLoginSuccess()
            }
        }
    }

    // ---------- UI ----------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B0B0F), // quase preto
                        Color(0xFF1C1C2E)  // azul escuro
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppTitle()
            Spacer(Modifier.height(12.dp))
            Subtitle()
            Spacer(Modifier.height(48.dp))

            GoogleLoginButton(
                isLoading = state is LoginUiState.Loading,
                onClick = {
                    launcher.launch(
                        googleSignInClient.signInIntent
                    )
                }
            )

            when (val uiState = state) {
                is LoginUiState.Error -> {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> Unit
            }
        }
    }
}


@Composable
fun AppTitle() {
    Text(
        text = "PixelVision",
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.5.sp
        ),
        color = Color(0xFFE50914) // vermelho cinema
    )
}

@Composable
fun Subtitle() {
    Text(
        text = "Crie suas listas.\nAvalie.\nDescubra novas histórias.",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White.copy(alpha = 0.85f),
        textAlign = TextAlign.Center
    )
}

@Composable
fun GoogleLoginButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF2F2F2)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp)
            )
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
                Text(
                    text = "Entrar com Google",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        state = LoginUiState.Error("Erro de autenticação")
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppTitle()
            Spacer(Modifier.height(48.dp))

            GoogleLoginButton(
                isLoading = state is LoginUiState.Loading,
                onClick = {}
            )

            if (state is LoginUiState.Error) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = state.message,
                    color = Color.Red
                )
            }
        }
    }
}