package com.example.embroid.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.embroid.data.models.EmbroideryDesign
import com.example.embroid.ui.components.DesignCard
import com.example.embroid.ui.viewmodels.SharedViewModel

@Composable
fun HomeScreen(sharedViewModel: SharedViewModel, onNavigateToDetail: (String) -> Unit) {

    val dummyDesigns = List(20) { index ->
        EmbroideryDesign(
            id = index.toString(),
            title = "Premium Design ${index + 1}",
            price = 12.99,
            thumbnailUrl = "https://picsum.photos/seed/${index}/400/400"
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(dummyDesigns) { design ->
            DesignCard(
                design = design,
                onCardClick = { onNavigateToDetail(design.id) }
            )
        }
    }
}