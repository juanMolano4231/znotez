package com.example.znotez.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.znotez.R

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FC)),
        contentAlignment = Alignment.Center
    ) {
        // Background decorative lines (C6B8FF with 50% opacity)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val color = Color(0xFFC6B8FF).copy(alpha = 0.5f)
            val strokeWidth = 160f

            // Line 1: Starts upper left, goes to right side and beyond
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(w * 1.2f, h / 3f),   // extended
                strokeWidth = strokeWidth
            )

            // Line 2: Starts lower right, goes to left side and beyond
            drawLine(
                color = color,
                start = Offset(w, h),
                end = Offset(-w * 0.2f, h * 2 / 3f),   // extended
                strokeWidth = strokeWidth
            )

            // Line 3: Connects the two ends (also extended)
            val path = Path().apply {
                moveTo(w * 1.2f, h / 3f)
                lineTo(-w * 0.2f, h * 2 / 3f)
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokeWidth)
            )
        }
        // Centered app logo (replace with your PNG)
        Box(
            modifier = Modifier.size(180.dp),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(R.drawable.znotez),
                contentDescription = "App Logo",
                modifier = Modifier.fillMaxSize()
            )

        }

        // Auto navigate
        LaunchedEffect(Unit) {
            delay(2000) // 2 seconds
            onNavigateToHome()
        }
    }
}