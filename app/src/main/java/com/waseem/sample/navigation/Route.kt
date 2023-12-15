package com.waseem.sample.navigation

sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Medicine : Route("medicine/{drugName}") {
        fun createRoute(drugName: String) = "medicine/$drugName"
    }
}