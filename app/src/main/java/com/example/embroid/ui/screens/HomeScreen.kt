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
import com.example.embroid.ui.viewmodels.SharedViewModel // NEW IMPORT

@Composable
// NEW: The door is open! It now accepts the sharedViewModel
fun HomeScreen(sharedViewModel: SharedViewModel) {

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
                // NEW: When the button is clicked, tell the Brain to add it!
                onAddToCartClick = {
                    sharedViewModel.addToCart(design)
                }
            )
        }
    }
}