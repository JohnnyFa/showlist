package com.fagundes.myshowlist.feat.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fagundes.myshowlist.components.NormalText
import com.fagundes.myshowlist.feat.home.domain.CarouselItemUi

val mockItems = listOf(
    CarouselItemUi("1", "Interstellar", 8.6, ""),
    CarouselItemUi("2", "Inception", 8.4, ""),
    CarouselItemUi("3", "Dune", 8.2, ""),
    CarouselItemUi("4", "Blade Runner", 8.1, "")
)

@Composable
fun TrendingNowSection() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            NormalText(text = "Trending Now", size = 24.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        MediaCarousel(
            items = mockItems,
            onItemClick = { /* navegar depois */ }
        )
    }
}