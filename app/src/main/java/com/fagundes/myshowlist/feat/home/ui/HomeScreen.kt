package com.fagundes.myshowlist.feat.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.feat.home.ui.components.RecommendedForYouSection
import com.fagundes.myshowlist.feat.home.ui.components.ShowOfTheDaySection
import com.fagundes.myshowlist.feat.home.ui.components.TrendingNowSection

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            item {
                ShowOfTheDaySection()
                Spacer(modifier = Modifier.height(24.dp))
                AppDivider()
            }

            item {
                TrendingNowSection()
                Spacer(modifier = Modifier.height(24.dp))
                AppDivider()
            }

            item {
                RecommendedForYouSection()
                Spacer(modifier = Modifier.height(24.dp))
                AppDivider()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onLogout) {
                    Text("Sair")
                }
            }
        }
    }
}

@Composable
fun AppDivider() {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color(0xFFE50914).copy(alpha = 0.35f)
    )
}
