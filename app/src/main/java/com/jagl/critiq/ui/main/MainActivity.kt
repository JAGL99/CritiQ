package com.jagl.critiq.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.jagl.critiq.core.ui.extensions.fullScreen
import com.jagl.critiq.feature.detail.MediaDetailScreen
import com.jagl.critiq.feature.detail.MediaDetailViewModel
import com.jagl.critiq.feature.home.HomeScreen
import com.jagl.critiq.feature.search.SearchScreen
import com.jagl.critiq.navigation.BottomNavigationBar
import com.jagl.critiq.navigation.Screen
import com.jagl.critiq.ui.theme.CritiQTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CritiQTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fullScreen(padding = 0.dp),
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { innerPadding ->

        val graph = navController.createGraph(startDestination = Screen.Home.route) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    onNavigateToDetail = { mediaId ->
                        navController.navigate(
                            route = Screen.Detail.route + "/$mediaId"
                        )
                    }
                )
            }
            composable(
                route = Screen.Detail.route + "/{${MediaDetailViewModel.MEDIA_ID}}",
                arguments = listOf(navArgument(MediaDetailViewModel.MEDIA_ID) {
                    type = NavType.LongType
                })
            ) {
                MediaDetailScreen()
            }
            composable(route = Screen.Search.route) {
                SearchScreen(
                    onMediaSelected = { mediaId ->
                        navController.navigate(
                            route = Screen.Detail.route + "/$mediaId"
                        )
                    }
                )
            }
            composable(route = Screen.Profile.route) {
                TODO("Profile Screen not implemented yet")
            }
        }

        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }

}
