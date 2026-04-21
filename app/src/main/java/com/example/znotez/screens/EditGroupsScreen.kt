package com.example.znotez.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.znotez.data.group.GroupRepository
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun EditGroupScreen(
    groupId: Long,
    repository: GroupRepository,
    onNavigateToHome: () -> Unit,
    onNavigateToGroups: () -> Unit,
    onNavigateToEditNote: (Long) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var groupName by rememberSaveable { mutableStateOf("") }
    var initialized by remember { mutableStateOf(false) }

    // Load existing group (edit mode)
    LaunchedEffect(groupId) {
        if (groupId != -1L) {
            val group = repository.getById(groupId)
            groupName = group?.name ?: ""
            initialized = true
        } else {
            groupName = ""
            initialized = true
        }
    }

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
                    onClick = { onNavigateToEditNote(-1L) }
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
                    start = Offset(w / 2f, h),
                    end = Offset(w * 1.2f, 0f),
                    strokeWidth = 160f
                )
            }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                val isLandscape = maxWidth > maxHeight
                val contentWidth = if (isLandscape) maxWidth * 0.5f else maxWidth

                Box(
                    modifier = Modifier
                        .width(contentWidth)
                        .align(Alignment.Center)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            value = groupName,
                            onValueChange = { groupName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFA8C5FF)),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 18.sp
                            ),
                            leadingIcon = {
                                Icon(Icons.Default.Folder, null, tint = Color.White)
                            },
                            placeholder = {
                                Text("Group name", color = Color.White.copy(alpha = 0.7f))
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            // SAVE / CREATE
                            Button(
                                onClick = {
                                    if (groupName.isNotBlank()) {
                                        if (groupId == -1L) {
                                            // create
                                            kotlinx.coroutines.GlobalScope.launch {
                                                repository.create(groupName)
                                            }
                                        } else {
                                            // update
                                            kotlinx.coroutines.GlobalScope.launch {
                                                repository.update(groupId, groupName)
                                            }
                                        }
                                        onSave()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFFAEE6D8)),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                            ) {
                                Icon(Icons.Default.CreateNewFolder, null, tint = Color.Black)
                            }

                            // CANCEL
                            Button(
                                onClick = onCancel,
                                colors = ButtonDefaults.buttonColors(Color(0xFFF4A6A6)),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                            ) {
                                Icon(Icons.Default.Cancel, null, tint = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}