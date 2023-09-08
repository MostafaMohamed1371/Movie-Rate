package com.example.movies2app.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movies2app.data.remote.retrofit.CategoryItems
import com.example.movies2app.data.remote.retrofit.MoviesItems

@Composable
fun Chip(
    genre: CategoryItems,
    modifier: Modifier,
    selected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    onSelected: ((movie: CategoryItems) -> Unit),

    ) {
    Surface(
        modifier = modifier.clickable {
            onSelected(genre)
        }.padding(contentPadding),
        shape = RoundedCornerShape(10.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shadowElevation = if (selected) 5.dp else 0.dp
    ) {
        Text(
            modifier = modifier.padding(contentPadding),
            text = genre.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
        HorizontalDivider()
    }
}