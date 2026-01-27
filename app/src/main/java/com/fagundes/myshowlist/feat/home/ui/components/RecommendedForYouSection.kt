package com.fagundes.myshowlist.feat.home.ui.components

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fagundes.myshowlist.R
import com.fagundes.myshowlist.components.AppDivider
import com.fagundes.myshowlist.components.NormalText
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.ui.theme.RecommendedPurple

@Composable
fun RecommendedForYouSection(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    Column {
        AppDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AutoAwesome,
                contentDescription = null,
                tint = RecommendedPurple,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            NormalText(text = stringResource(R.string.label_for_you), size = 24.sp)
        }
        AppDivider()
        Spacer(modifier = Modifier.height(24.dp))
        MediaCarousel(
            items = movies,
            onItemClick = { movie ->
                onMovieClick(movie)
            }
        )
    }
}