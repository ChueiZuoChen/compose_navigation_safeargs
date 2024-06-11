package com.example.composenavigationsafeargs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            MyNavHost(navHostController = navHostController)
        }
    }
}

@Composable
fun MyNavHost(
    navHostController: NavHostController,
) {
    NavHost(navController = navHostController, startDestination = Routes.FirstScreen) {
        composable<Routes.FirstScreen> {
            FirstScreen(
                onNavigateForward = {
                    navHostController.navigate(it)
                }
            )
        }
        composable<Routes.SecondScreen>(
            typeMap = mapOf(typeOf<MyCustomModel>() to CustomNavType(MyCustomModel::class.java, MyCustomModel.serializer()))
        ) {
            val args = it.toRoute<Routes.SecondScreen>()
            SecondScreen(
                args = args,
                onNavigateBack = {
                    navHostController.navigate(Routes.FirstScreen)
                }
            )
        }
    }
}