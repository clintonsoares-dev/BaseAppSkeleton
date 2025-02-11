package com.csdev.baseappskeleton.ui

import androidx.navigation.NavHostController

object Destination {
    const val GREETING = "greeting"
}

object DestinationArgs {
    const val NAME = "name"
}

class Route(navController: NavHostController) {
    val toGreeting: (String) -> Unit = { name ->
        navController.navigate("${Destination.GREETING}/$name")
    }
}