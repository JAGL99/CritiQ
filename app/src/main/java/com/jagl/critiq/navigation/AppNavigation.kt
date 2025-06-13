package com.jagl.critiq.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jagl.critiq.feature.detail.MediaDetailScreen
import com.jagl.critiq.feature.detail.MediaDetailViewModel
import com.jagl.critiq.feature.home.HomeScreen
import com.jagl.critiq.feature.search.SearchScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "search") {
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            "detail/{${MediaDetailViewModel.MEDIA_ID}}",
            arguments = listOf(navArgument(MediaDetailViewModel.MEDIA_ID) {
                type = NavType.LongType
            })
        ) {
            MediaDetailScreen()
        }
        composable("search") {
            SearchScreen(onBackPressed = { navController.popBackStack() })
        }
    }
}