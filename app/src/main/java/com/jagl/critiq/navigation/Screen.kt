package com.jagl.critiq.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail")
    data object Search : Screen("search")
    data object Profile : Screen("profile")
}