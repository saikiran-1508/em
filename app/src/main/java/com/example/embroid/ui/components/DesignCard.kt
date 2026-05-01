package com.example.embroid.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.embroid.data.models.EmbroideryDesign

// --- TRADITIONAL COLOR PALETTE ---
val DeepMaroon = Color(0xFF7B0828)
val WarmGold = Color(0xFFC5A059)
val CreamWhite = Color(0xFFFCF9F2)

@Composable
fun DesignCard(design: EmbroideryDesign, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = CreamWhite),
        // Add a subtle gold border for a premium feel
        border = BorderStroke(1.dp, WarmGold.copy(alpha = 0.5f))
    ) {
        Column {
            AsyncImage(
                model = design.thumbnailUrl,
                contentDescription = "Image of ${design.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Made slightly taller to show off saree work
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = design.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif, // Elegant traditional font
                    fontWeight = FontWeight.Bold,
                    color = DeepMaroon
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "₹${design.price}", // Changed to Rupees for context
                    style = MaterialTheme.typography.bodyLarge,
                    color = WarmGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}