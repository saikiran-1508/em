package com.example.embroid.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.embroid.ui.components.BottomNavigationBar
import com.example.embroid.ui.navigation.Screen
import com.example.embroid.ui.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topBarTitle = when {
        currentRoute == Screen.Home.route -> "Embroid Catalog"
        currentRoute == Screen.AISearch.route -> "AI Visual Match"
        currentRoute == Screen.Cart.route -> "Your Cart"
        currentRoute?.startsWith("detail_screen") == true -> "Product Details"
        else -> "Embroid"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    sharedViewModel = sharedViewModel,
                    onNavigateToDetail = { designId ->
                        navController.navigate("detail_screen/$designId")
                    }
                )
            }

            composable(Screen.AISearch.route) {
                AISearchScreen(
                    sharedViewModel = sharedViewModel,
                    onNavigateToDetail = { designId ->
                        navController.navigate("detail_screen/$designId")
                    }
                )
            }

            composable(Screen.Cart.route) {
                CartScreen(sharedViewModel = sharedViewModel)
            }

            composable("detail_screen/{designId}") { backStackEntry ->
                val designId = backStackEntry.arguments?.getString("designId") ?: "0"
                DesignDetailScreen(
                    designId = designId,
                    sharedViewModel = sharedViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}