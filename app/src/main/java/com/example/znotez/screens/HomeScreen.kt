package com.example.znotez.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToGroups: () -> Unit,
    onNavigateToNotes: () -> Unit   // we'll use this for "edit note" / new note
) {
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
                    icon = { Icon(Icons.Default.List, contentDescription = "Groups") },
                    label = { Text("Groups") },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Note, contentDescription = "New Note") },
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
            // Decorative line
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

            // Title box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFA8C5FF))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Note,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Last Notes",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 2x2 Grid of Note Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(4) { index ->
                    NoteCard(
                        title = "Note ${index + 1}",
                        previewText = "This is a preview of the note content...",
                        hasImage = index % 2 == 0
                    )
                }
            }
        }
    }
}

// Simple Note Card stub
@Composable
private fun NoteCard(title: String, previewText: String, hasImage: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC6B8FF))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (hasImage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📷 Image", color = Color.White)
                }
            } else {
                Text(
                    text = previewText,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}