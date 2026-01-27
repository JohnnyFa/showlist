package com.fagundes.myshowlist.components.error

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fagundes.myshowlist.components.ErrorText

@Composable
fun ErrorSection(
    message: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        ErrorText(
            text = "Something went wrong",
            size = 18.sp,
            weight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        ErrorText(
            text = message,
            size = 14.sp,
            weight = FontWeight.Normal
        )

        if (action != null) {
            Spacer(modifier = Modifier.height(16.dp))
            action()
        }
    }
}
