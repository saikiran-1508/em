package com.example.embroid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.embroid.data.models.EmbroideryDesign
import com.example.embroid.ui.components.DesignCard
import com.example.embroid.ui.viewmodels.SharedViewModel

// --- TRADITIONAL COLOR PALETTE ---
val DeepMaroon = Color(0xFF7B0828)
val WarmGold = Color(0xFFC5A059)
val CreamWhite = Color(0xFFFCF9F2)
val CharcoalText = Color(0xFF333333)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignDetailScreen(
    designId: String,
    sharedViewModel: SharedViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    val idNum = designId.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 1
    val design = EmbroideryDesign(
        id = designId,
        title = "Royal Zari Motifs ${idNum + 1}",
        price = 1499.00,
        thumbnailUrl = "https://picsum.photos/seed/$idNum/400/400"
    )

    val recommendations = List(5) { index ->
        val recId = idNum + index + 50
        EmbroideryDesign(
            id = recId.toString(),
            title = "Bridal Silk Pattern ${index + 1}",
            price = 1299.00,
            thumbnailUrl = "https://picsum.photos/seed/$recId/400/400"
        )
    }

    Scaffold(
        containerColor = CreamWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text("Design Details", fontFamily = FontFamily.Serif, color = WarmGold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepMaroon
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go Back", tint = WarmGold)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = design.thumbnailUrl,
                contentDescription = design.title,
                modifier = Modifier.fillMaxWidth().height(350.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = design.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = DeepMaroon
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "₹${design.price}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = WarmGold,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Exquisite traditional embroidery file designed specifically for heavy silk sarees and bridal blouses. Includes intricate Zari and Resham thread paths. Compatible with professional multi-needle machines.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CharcoalText,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Elegant Add to Cart Button
                Button(
                    onClick = {
                        sharedViewModel.addToCart(design)
                        Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepMaroon,
                        contentColor = WarmGold
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Add to Cart", fontFamily = FontFamily.Serif, fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- FULL-STACK BUY NOW BUTTON ---
                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "Contacting Server...", Toast.LENGTH_SHORT).show()

                        sharedViewModel.buyDesignDirectly(design) { success, message ->
                            // This shows the exact message received from your Node.js backend!
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = DeepMaroon
                    ),
                    border = BorderStroke(2.dp, DeepMaroon),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Buy Now", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }
            }

            HorizontalDivider(thickness = 2.dp, color = WarmGold.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 16.dp))

            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "You May Also Love",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = DeepMaroon,
                    modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recommendations) { rec ->
                        Box(modifier = Modifier.width(180.dp)) {
                            DesignCard(
                                design = rec,
                                onCardClick = { /* Stop infinite nesting in prototype */ }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}