package com.example.znotez.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onNavigateToGroups: () -> Unit, onNavigateToNotes: () -> Unit) {
    Scaffold() { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text("Home Screen")
            Button(onClick = onNavigateToGroups) { Text("Go to Groups") }
            Button(onClick = onNavigateToNotes) { Text("Go to Notes") }
        }
    }
}