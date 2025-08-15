package com.example.localhtml.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "input") {
        composable("input") { UserFormScreen(navController) }
        composable(
            "webview/{name}/{age}/{address}/{email}/{phone}/{entryTime}"
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "Guest"
            val age = backStackEntry.arguments?.getString("age") ?: "--"
            val address = backStackEntry.arguments?.getString("address") ?: "--"
            val email = backStackEntry.arguments?.getString("email") ?: "--"
            val phone = backStackEntry.arguments?.getString("phone") ?: "--"
            val entryTime = backStackEntry.arguments?.getString("entryTime") ?: "--:--:--"
            WebViewScreen(name, age, address, email, phone, entryTime)
        }
    }
}