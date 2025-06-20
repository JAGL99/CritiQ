package com.jagl.critiq.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

fun getNavItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = "home"
        ),
        NavigationItem(
            title = "Search",
            icon = Icons.Default.Search,
            route = "search"
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Home, // TODO: Replace with actual profile icon
            route = "profile"
        )
    )
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)