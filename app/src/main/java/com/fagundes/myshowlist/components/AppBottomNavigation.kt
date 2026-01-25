package com.fagundes.myshowlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.core.navigation.AppRoutes

@Composable
fun AppBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {

        NavigationBar(
            modifier = Modifier
                .height(52.dp)
                .padding(horizontal = 100.dp)
                .clip(RoundedCornerShape(28.dp))
                .shadow(20.dp, RoundedCornerShape(28.dp)),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {

            BottomIcon(
                selected = currentRoute == AppRoutes.HOME,
                icon = Icons.Default.Home,
                onClick = { onNavigate(AppRoutes.HOME) }
            )

            BottomIcon(
                selected = currentRoute == AppRoutes.CATALOG,
                icon = Icons.Default.Search,
                onClick = { onNavigate(AppRoutes.CATALOG) }
            )
        }
    }
}

@Composable
private fun RowScope.BottomIcon(
    selected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .then(
                        if (selected) {
                            Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected)
                        Color(0xFFE50914) // vermelho cinema
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}