package com.example.znotez.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditNoteScreen(
    noteId: String?,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToEditNote: () -> Unit,
    onNavigateToNotes: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

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
                    selected = false,
                    onClick = onNavigateToGroups
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.NoteAdd, null) },
                    selected = true,
                    onClick = onNavigateToEditNote
                )
            }
        }
    ) { padding ->

        Box(Modifier.fillMaxSize()) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F8FC))
            ) {
                val w = size.width
                val h = size.height

                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(w * 0.25f, 0f),
                    end = Offset(w * 0.45f, h),
                    strokeWidth = 140f
                )

                drawLine(
                    color = Color(0xFFC6B8FF).copy(alpha = 0.5f),
                    start = Offset(0f, h * 0.33f),
                    end = Offset(w * 0.5f, h),
                    strokeWidth = 140f
                )
            }

            if (!isLandscape) {

                // VERTICAL (restored behavior)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    Title()

                    Spacer(Modifier.height(12.dp))

                    AttachRow()

                    Spacer(Modifier.height(12.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        ContentBox(text) { text = it }
                    }

                    Spacer(Modifier.height(12.dp))

                    BottomButtons(
                        onSave = onNavigateToNotes,
                        onDelete = onNavigateToNotes,
                        onCancel = onNavigateToNotes
                    )
                }

            } else {

                // LANDSCAPE
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // Centered attach buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                AttachButton(Icons.Default.AddPhotoAlternate, 60.dp)
                                AttachButton(Icons.Default.AudioFile, 60.dp)
                            }
                        }

                        Button(
                            onClick = onNavigateToNotes,
                            colors = ButtonDefaults.buttonColors(Color(0xFFAEE6D8)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        ) { Icon(Icons.Default.Save, null) }

                        Button(
                            onClick = onNavigateToNotes,
                            colors = ButtonDefaults.buttonColors(Color(0xFFF4A6A6)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        ) { Icon(Icons.Default.Cancel, null) }

                        Button(
                            onClick = onNavigateToNotes,
                            colors = ButtonDefaults.buttonColors(Color(0xFFF4A6A6)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        ) { Icon(Icons.Default.DeleteForever, null) }
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight()
                    ) {

                        Title()

                        Spacer(Modifier.height(8.dp))

                        ContentBox(text) { text = it }
                    }
                }
            }
        }
    }
}

@Composable
private fun Title() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFA8C5FF))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EditNote, null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Edit Note", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
private fun AttachRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AttachButton(Icons.Default.AddPhotoAlternate)
            AttachButton(Icons.Default.AudioFile)
        }
    }
}

@Composable
private fun AttachButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    size: Dp = 80.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFAEE6D8)),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null)
    }
}

@Composable
private fun ContentBox(
    text: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFC6B8FF))
            .padding(12.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFAEE6D8))
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {

            val mediaSize = 70.dp
            val overlayHeight = 28.dp

            Box(
                modifier = Modifier
                    .size(mediaSize)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .height(overlayHeight)
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Icon(
                        Icons.Default.Close,
                        null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(16.dp)
                    )
                }
            }

            Spacer(Modifier.width(6.dp))

            Box(
                modifier = Modifier
                    .size(mediaSize)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFAEE6D8)),
                contentAlignment = Alignment.Center
            ) {
                Text("audio.mp3", fontSize = 10.sp)

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .height(overlayHeight)
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Icon(
                        Icons.Default.Close,
                        null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomButtons(
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = onSave,
            colors = ButtonDefaults.buttonColors(Color(0xFFAEE6D8)),
            modifier = Modifier.weight(1f)
        ) { Icon(Icons.Default.Save, null) }

        Button(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(Color(0xFFF4A6A6)),
            modifier = Modifier.weight(1f)
        ) { Icon(Icons.Default.DeleteForever, null) }

        Button(
            onClick = onCancel,
            colors = ButtonDefaults.buttonColors(Color(0xFFF4A6A6)),
            modifier = Modifier.weight(1f)
        ) { Icon(Icons.Default.Cancel, null) }
    }
}
