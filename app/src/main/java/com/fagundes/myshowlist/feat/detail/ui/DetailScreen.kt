package com.fagundes.myshowlist.feat.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.fagundes.myshowlist.components.shimmer.PosterShimmer
import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi
import com.fagundes.myshowlist.feat.detail.vm.DetailUiState
import com.fagundes.myshowlist.feat.detail.vm.DetailViewModel
import java.util.Locale

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is DetailUiState.Loading -> {
            Box {}
        }

        is DetailUiState.Error -> {
            Text(
                text = (state as DetailUiState.Error).message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        is DetailUiState.Success -> {
            DetailContent(
                ui = (state as DetailUiState.Success).ui,
                onBack = onBack
            )
        }
    }
}

@Composable
fun DetailContent(
    ui: ContentDetailUi,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {

        PosterBanner(
            imageUrl = ui.imageUrl,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            Text(
                text = ui.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            RatingAndTypeRow(
                rating = ui.rating,
                type = ui.type
            )

            Spacer(modifier = Modifier.height(16.dp))

            OverviewSection(overview = ui.overview)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
private fun PosterBanner(
    imageUrl: String?,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {

        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = { PosterShimmer() },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp, top = 24.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun RatingAndTypeRow(
    rating: Double?,
    type: String?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (rating != null) {
            Text(
                text = "⭐ ${String.format(Locale.US, "%.1f", rating)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (type != null) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = type.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun OverviewSection(overview: String?) {
    val textColor = MaterialTheme.colorScheme.onSurface

    if (!overview.isNullOrBlank()) {
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            lineHeight = 20.sp
        )
    } else {
        Text(
            text = "No description available.",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.6f)
        )
    }
}