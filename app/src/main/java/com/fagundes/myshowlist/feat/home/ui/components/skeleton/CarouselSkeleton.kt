package com.fagundes.myshowlist.feat.home.ui.components.skeleton

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.components.shimmer.ShimmerBox

@Composable
fun CarouselSkeleton() {
    Row {
        repeat(4) {
            ShimmerBox(
                width = 120.dp,
                height = 180.dp,
                radius = 12.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}