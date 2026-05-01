package com.example.embroid.data.models

data class EmbroideryDesign(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnailUrl: String // We use a URL string because these will live in the cloud
)