package com.example.znotez.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.BoxWithConstraints
import com.example.znotez.data.group.GroupRepository
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GroupsScreen(
    repository: GroupRepository,
    onNavigateToHome: () -> Unit,
    onNavigateToEditGroup: (Long) -> Unit,
    onNavigateToEditNote: () -> Unit,
    onNavigateToNotes: (Long) -> Unit
) {
    val groups by repository.getAll().collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFA8C5FF)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    selected = false,
                    onClick = onNavigateToHome
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ViewCozy, null) },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.NoteAdd, null) },
                    selected = false,
                    onClick = onNavigateToEditNote
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F8FC))
            ) {
                val w = size.width
                val h = size.height
                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(-w * 0.5f, h * 0.65f),
                    end = Offset(w * 1.5f, h * 0.85f),
                    strokeWidth = 160f
                )
            }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                val isLandscape = maxWidth > maxHeight
                val columns = if (isLandscape) 3 else 2

                Column(modifier = Modifier.fillMaxSize()) {

                    if (isLandscape) {
                        // Top row: title (2/3) + button (1/3)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            // Title (2 columns)
                            Box(
                                modifier = Modifier
                                    .weight(2f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFA8C5FF))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.ViewCozy, null, tint = Color.White)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Groups", color = Color.White, fontSize = 20.sp)
                                }
                            }

                            // Button (1 column)
                            Button(
                                onClick = { onNavigateToEditGroup(-1L) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEE6D8)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                            ) {
                                Icon(Icons.Default.CreateNewFolder, null, tint = Color.Black)
                            }
                        }
                    } else {
                        // Portrait title
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFA8C5FF))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.ViewCozy, null, tint = Color.White)
                                Spacer(Modifier.width(8.dp))
                                Text("Groups", color = Color.White, fontSize = 20.sp)
                            }
                        }
                    }

                    // Grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (groups.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("No groups", color = Color.Gray)
                                }
                            }
                        } else {
                            items(groups) { group ->
                                GroupCard(
                                    title = group.name,
                                    isLandscape = isLandscape,
                                    onClick = { onNavigateToNotes(group.id) }
                                )
                            }
                        }
                    }

                    if (!isLandscape) {
                        // Portrait button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = { onNavigateToEditGroup(-1L) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEE6D8)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .height(56.dp)
                            ) {
                                Icon(Icons.Default.CreateNewFolder, null, tint = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GroupCard(
    title: String,
    isLandscape: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isLandscape) Modifier.height(60.dp)
                else Modifier.height(80.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC6B8FF))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Folder, null, tint = Color.White)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = Color.White)
        }
    }
}