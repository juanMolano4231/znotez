package com.example.znotez.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.znotez.data.AppDatabase
import com.example.znotez.data.model.Note
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.map
import androidx.compose.foundation.lazy.grid.items

@Composable
fun HomeScreen(
    onNavigateToGroups: () -> Unit,
    onNavigateToNotes: () -> Unit,
    context: android.content.Context
) {
    val db = AppDatabase.getDatabase(context)
    val dao = db.noteDao()

    val notes by dao.getAllNotes()
        .map { it.take(4) }
        .collectAsState(initial = emptyList<Note>())

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFA8C5FF)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Groups") },
                    label = { Text("Groups") },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "New Note") },
                    label = { Text("Note") },
                    selected = false,
                    onClick = onNavigateToNotes
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF7F8FC))
        ) {
            Canvas(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                val w = size.width
                val h = size.height
                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(0f, h / 3f),
                    end = Offset(w, h * 2 / 3f),
                    strokeWidth = 16f
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFA8C5FF))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Last Notes",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 2x2 Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (notes.isEmpty()) {
                    items(4) { EmptyNoteCard() }
                } else {
                    items(notes) { note ->
                        NoteCard(note = note)
                    }
                    // Fill remaining slots to always show 4 cards
                    repeat(4 - notes.size) {
                        item { EmptyNoteCard() }
                    }
                }
            }
        }
    }
}

// Keep NoteCard and EmptyNoteCard the same as before

// Real Note Card
@Composable
private fun NoteCard(note: Note) {
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC6B8FF))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = note.title.ifEmpty { "Untitled" },
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.content,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Empty placeholder card
@Composable
private fun EmptyNoteCard() {
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC6B8FF).copy(alpha = 0.6f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No note", color = Color.White.copy(alpha = 0.5f))
        }
    }
}