package com.example.embroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// 1. Define the data we are sending to the server (Matches req.body)
data class CheckoutRequest(
    val designId: String,
    val title: String,
    val price: Double
)

// 2. Define the data we expect back from the server (Matches res.json)
data class CheckoutResponse(
    val success: Boolean,
    val message: String,
    val pdfStatus: String
)

// 3. Define the Doorway (The POST request)
interface EmbroidApi {
    @POST("/api/checkout")
    suspend fun processCheckout(@Body request: CheckoutRequest): CheckoutResponse
}

// 4. Build the actual Retrofit Bridge pointing to your Windows computer
val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.8:3000") // 10.0.2.2 is the emulator's secret code for Windows localhost
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// 5. Create a ready-to-use tool
val apiService = retrofit.create(EmbroidApi::class.java)