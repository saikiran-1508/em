package com.example.embroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.embroid.data.models.EmbroideryDesign
import com.example.embroid.network.CheckoutRequest
import com.example.embroid.network.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {

    // --- Cart State Management ---
    private val _cartItems = MutableStateFlow<List<EmbroideryDesign>>(emptyList())
    val cartItems: StateFlow<List<EmbroideryDesign>> = _cartItems.asStateFlow()

    fun addToCart(design: EmbroideryDesign) {
        _cartItems.value = _cartItems.value + design
    }

    fun removeFromCart(designId: String) {
        _cartItems.value = _cartItems.value.filter { it.id != designId }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    // --- NEW: API Server Communication ---
    fun buyDesignDirectly(design: EmbroideryDesign, onResult: (Boolean, String) -> Unit) {
        // Launch on a background thread so the UI doesn't freeze
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 1. Pack up the order details
                val request = CheckoutRequest(
                    designId = design.id,
                    title = design.title,
                    price = design.price
                )

                // 2. Send it over the bridge to the server!
                val response = apiService.processCheckout(request)

                // 3. Switch back to the main UI thread to show the result
                withContext(Dispatchers.Main) {
                    onResult(response.success, response.message)
                }
            } catch (e: Exception) {
                // If the server is turned off or there is no internet, catch the error
                withContext(Dispatchers.Main) {
                    onResult(false, "Connection Failed: Is the server running? ${e.localizedMessage}")
                }
            }
        }
    }
}