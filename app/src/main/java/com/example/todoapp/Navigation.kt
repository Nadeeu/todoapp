package com.example.todoapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.auth.Login
import com.example.todoapp.auth.SignUp
import com.example.todoapp.home.Home

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { Login(navController = navController) }
        composable("signup") { SignUp(navController = navController) }
        composable("home") { Home(navController = navController) }
    }
}
