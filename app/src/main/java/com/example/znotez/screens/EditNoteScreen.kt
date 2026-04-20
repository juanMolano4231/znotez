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
fun EditNoteScreen(noteId: String?, onSave: () -> Unit, onCancel: () -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text("Edit Note Screen - ID: ${noteId ?: "New"}")
            Button(onClick = onSave) { Text("Save Note") }
            Button(onClick = onCancel) { Text("Cancel") }
        }
    }
}