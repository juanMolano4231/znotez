package com.example.znotez.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NotesScreen(
    onNavigateToEditNote: (String?) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToGroups: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFA8C5FF)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = false,
                    onClick = onNavigateToHome
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ViewCozy, contentDescription = "Groups") },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.NoteAdd, contentDescription = "New Note") },
                    selected = true,
                    onClick = { onNavigateToEditNote(null) }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            // Updated decorative line
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F8FC))
            ) {
                val w = size.width
                val h = size.height
                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(0f, h * 0.5f),          // mid left
                    end = Offset(w * 0.6f, h),            // slightly over half bottom
                    strokeWidth = 160f
                )
            }

            val isLandscape =
                LocalConfiguration.current.orientation ==
                        android.content.res.Configuration.ORIENTATION_LANDSCAPE

            if (!isLandscape) {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    // Title
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFA8C5FF))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FolderOpen, null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Construction",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(20) { index ->
                            NoteCard(
                                title = "Note ${index + 1}",
                                onClick = { onNavigateToEditNote(null) }
                            )
                        }
                    }

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { onNavigateToEditNote(null) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEE6D8)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.NoteAdd, null, tint = Color.Black)
                        }

                        Button(
                            onClick = onNavigateToGroups,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A6A6)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.DeleteForever, null)
                        }
                    }
                }


            } else {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    // LEFT COLUMN
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Red button (top-left)
                        Button(
                            onClick = onNavigateToGroups,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A6A6)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Icon(Icons.Default.DeleteForever, null)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Green button (row 3, col 1)
                        Button(
                            onClick = { onNavigateToEditNote(null) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEE6D8)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Icon(Icons.Default.NoteAdd, null, tint = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // MIDDLE + RIGHT (2 columns merged)
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight()
                    ) {

                        // Title (spans 2 columns visually)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFA8C5FF))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.FolderOpen, null, tint = Color.White)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Construction",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Grid: 2 columns, ~3 visible rows
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(20) { index ->
                                NoteCard(
                                    title = "Note ${index + 1}",
                                    onClick = { onNavigateToEditNote(null) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun NoteCard(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC6B8FF))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.FileOpen, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = Color.White)
        }
    }
}