package com.fagundes.myshowlist.feat.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fagundes.myshowlist.ui.theme.AccentGold
import com.fagundes.myshowlist.ui.theme.SurfaceElevated

@Composable
fun UpcomingHighlightCard(
    onSeeAll: () -> Unit
) {
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "FEATURED TODAY",
                color = AccentGold,
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.weight(1f))

            TextButton(onClick = onSeeAll) {
                Text(
                    text = "Ver todos",
                    color = AccentGold
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(SurfaceElevated)
        ) {
        }
    }
}
