package com.example.znotez.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupsScreen(onEditGroup: (String?) -> Unit, onNavigateToHome: () -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text("Groups Screen")
            Button(onClick = { onEditGroup(null) }) { Text("New Group") }
            Button(onClick = onNavigateToHome) { Text("Back to Home") }
        }
    }
}