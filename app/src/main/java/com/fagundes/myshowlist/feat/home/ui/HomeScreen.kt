package com.fagundes.myshowlist.feat.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.feat.home.ui.components.Banner
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Banner()
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFE50914).copy(alpha = 0.35f)
            )
            RecommendedForYouSection()
            Spacer(modifier = Modifier.height(24.dp))
            TrendingNowSection()
            Spacer(modifier = Modifier.height(24.dp))
            ShowOfTheDaySection()

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onLogout) {
                Text("Sair")
            }
        }
    }
}
