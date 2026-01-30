package com.fagundes.myshowlist.feat.catalog.ui.components
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.fagundes.myshowlist.R
import com.fagundes.myshowlist.ui.theme.TextMuted
import com.fagundes.myshowlist.ui.theme.TextPrimary


@Composable
fun CatalogEmpty() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_splash_logo),
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(80.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "No Movies Found",
            color = TextPrimary,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Try adjusting your filters or search terms.",
            color = TextMuted,
            textAlign = TextAlign.Center
        )
    }
}
