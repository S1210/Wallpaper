package com.akvelon.wallpaper.presentation.main.generate.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akvelon.wallpaper.domain.model.Event

@Composable
fun EventItem(
    event: Event,
    onClick: (event: Event) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick(event) }
    ) {
        Text(modifier = Modifier.padding(8.dp), text = event.description)
    }
}