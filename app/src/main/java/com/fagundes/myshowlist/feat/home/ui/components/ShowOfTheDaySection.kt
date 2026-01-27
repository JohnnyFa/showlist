package com.fagundes.myshowlist.feat.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fagundes.myshowlist.components.AppDivider
import com.fagundes.myshowlist.components.NormalText
import com.fagundes.myshowlist.core.TMDB_IMAGE_BASE
import com.fagundes.myshowlist.core.domain.Movie
import java.util.Locale

@Composable
fun ShowOfTheDaySection(
    movie: Movie
) {
    Column {
        AppDivider()
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
        AppDivider()
        ShowDetails(movie)
    }
}

@Composable
fun ShowDetails(movie: Movie) {
    val formattedRating = String.format(Locale.US, "%.1f", movie.rating)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {

        // IMAGEM (camada única)
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${TMDB_IMAGE_BASE}${movie.posterUrl}")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // TÍTULO + NOTA
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NormalText(text = movie.title, size = 14.sp, weight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            NormalText(text = "⭐ $formattedRating", size = 14.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // SINOPSE
        NormalText(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = movie.overview ?: "",
            size = 12.sp,
            align = TextAlign.Start,
            letterSpacing = 1.sp
        )
    }
}
