package com.fagundes.myshowlist.feat.home.ui.components.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fagundes.myshowlist.components.NormalText

@Composable
fun ShowOfTheDaySkeleton() {
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
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            NormalText(text = "Show of the day", size = 24.sp)
        }

        ShimmerBox(
            width = 360.dp,
            height = 220.dp,
            radius = 16.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            ShimmerBox(width = 160.dp, height = 20.dp)
            Spacer(modifier = Modifier.width(12.dp))
            ShimmerBox(width = 40.dp, height = 20.dp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        repeat(3) {
            ShimmerBox(width = 300.dp, height = 14.dp)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}