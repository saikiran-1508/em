package com.example.embroid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.embroid.data.models.EmbroideryDesign
import com.example.embroid.ui.components.DesignCard
import com.example.embroid.ui.viewmodels.SharedViewModel

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
        title = "Premium Design ${idNum + 1}",
        price = 15.99,
        thumbnailUrl = "https://picsum.photos/seed/$idNum/400/400"
    )

    val recommendations = List(5) { index ->
        val recId = idNum + index + 50
        EmbroideryDesign(
            id = recId.toString(),
            title = "Similar Match ${index + 1}",
            price = 12.99,
            thumbnailUrl = "https://picsum.photos/seed/$recId/400/400"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
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
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = design.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$${design.price}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "High-quality digitized embroidery file. Compatible with PES, DST, EXP, and JEF formats. Perfect for jackets, hats, and heavy fabrics.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        sharedViewModel.addToCart(design)
                        Toast.makeText(context, "Added to Cart!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add to Cart", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }

                Spacer(modifier = Modifier.height(12.dp))

                FilledTonalButton(
                    onClick = {
                        Toast.makeText(context, "Redirecting to Buy Now...", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Buy Now", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }
            }

            Divider(thickness = 8.dp, color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))

            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(
                    text = "Customers also viewed",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp).padding( bottom = 12.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recommendations) { rec ->
                        Box(modifier = Modifier.width(160.dp)) {
                            DesignCard(
                                design = rec,
                                onCardClick = { /* Prototype limitation: stop infinite nesting */ }
                            )
                        }
                    }
                }
            }
        }
    }
}