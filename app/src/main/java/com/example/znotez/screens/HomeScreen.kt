package com.example.znotez.screens

import androidx.compose.material.icons.filled.NoteAdd

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToGroups: () -> Unit,
    onNavigateToEditNote: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFA8C5FF)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ViewCozy, contentDescription = "Groups") },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.NoteAdd, contentDescription = "New Note") },
                    selected = false,
                    onClick = onNavigateToEditNote
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background decorative line
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F8FC))
            ) {
                val w = size.width
                val h = size.height
                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(-w * 0.5f, h / 3f),      // extended to the left
                    end = Offset(w * 1.5f, h * 2 / 3f),     // extended to the right
                    strokeWidth = 160f
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
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
                        Icon(Icons.AutoMirrored.Filled.LibraryBooks, contentDescription = null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Last Notes",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 2x2 Grid with mock notes
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
                            content = "This is a preview of the note content. It shows the beginning of the text...",
                            onClick = { onNavigateToEditNote() }
                        )
                    }
                }
            }
        }
    }
}

// Mock Note Card
@Composable
private fun NoteCard(
    title: String,
    content: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC6B8FF))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}