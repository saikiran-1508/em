package com.example.embroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.embroid.data.models.EmbroideryDesign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {

    // 1. The Private Data: Only the Brain can actually change this list
    private val _cartItems = MutableStateFlow<List<EmbroideryDesign>>(emptyList())

    // 2. The Public Data: The UI screens can look at this list, but cannot change it directly
    val cartItems: StateFlow<List<EmbroideryDesign>> = _cartItems.asStateFlow()

    // 3. The Action: How the UI politely asks the Brain to add an item
    fun addToCart(design: EmbroideryDesign) {
        val currentList = _cartItems.value
        // Prevent adding the exact same item twice (optional e-commerce logic)
        if (!currentList.any { it.id == design.id }) {
            _cartItems.value = currentList + design
        }
    }

    // 4. The Action: How the UI politely asks the Brain to remove an item
    fun removeFromCart(designId: String) {
        _cartItems.value = _cartItems.value.filter { it.id != designId }
    }
    // NEW: The Action to completely empty the cart after a successful purchase
    fun clearCart() {
        _cartItems.value = emptyList()
    }
}