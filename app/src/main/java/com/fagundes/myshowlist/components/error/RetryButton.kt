package com.fagundes.myshowlist.components.error

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RetryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Refresh,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = "Try again")
    }
}
