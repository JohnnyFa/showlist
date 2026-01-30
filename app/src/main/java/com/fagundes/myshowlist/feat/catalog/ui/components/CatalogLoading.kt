package com.fagundes.myshowlist.feat.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.ui.theme.SurfaceElevated
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun CatalogLoading() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(SurfaceElevated)
        )

        Spacer(Modifier.height(24.dp))

        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceElevated)
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}
