package com.example.composenavigationsafeargs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FirstScreen(onNavigateForward: (Routes.SecondScreen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "First Screen")
        Button(onClick = {
            onNavigateForward(
                Routes.SecondScreen(MyCustomModel("Potti", id = 22))
            )
        }) {
            Text(text = "Go to Second Screen")
        }
    }

}

@Composable
fun SecondScreen(args: Routes.SecondScreen, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Result: $args")
        Text(text = "Second Screen")
        Button(onClick = { onNavigateBack() }) {
            Text(text = "Go to First Screen")
        }
    }

}
