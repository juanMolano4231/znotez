package com.example.znotez.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditNoteScreen(
    noteId: String?,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToEditNote: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFA8C5FF)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    selected = false,
                    onClick = onNavigateToHome
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    selected = true,
                    onClick = onNavigateToEditNote
                )
            }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize()) {

            // Background lines
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F8FC))
            ) {
                val w = size.width
                val h = size.height

                // Line 1
                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(w * 0.25f, 0f),
                    end = Offset(w * 0.45f, h),
                    strokeWidth = 140f
                )

                // Line 2
                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(0f, h * 0.33f),
                    end = Offset(w * 0.5f, h),
                    strokeWidth = 140f
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                // Title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFA8C5FF))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Home, null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text("Edit Note", color = Color.White, fontSize = 20.sp)
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Attach buttons
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFAEE6D8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Home, null)
                    }

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFAEE6D8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Home, null)
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Main content box
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFC6B8FF))
                        .padding(12.dp)
                ) {

                    // Text field (scrollable)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFAEE6D8))
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp)
                    ) {
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    // Attachments (scrollable)
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                    ) {

                        // Mock image
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(Color.White.copy(alpha = 0.7f))
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    null,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }

                        Spacer(Modifier.width(8.dp))

                        // Mock audio
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFAEE6D8)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("audio.mp3")

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(Color.White.copy(alpha = 0.7f))
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    null,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Bottom buttons
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEE6D8)),
                        modifier = Modifier.weight(1f)
                    ) { }

                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A6A6)),
                        modifier = Modifier.weight(1f)
                    ) { }

                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A6A6)),
                        modifier = Modifier.weight(1f)
                    ) { }
                }
            }
        }
    }
}