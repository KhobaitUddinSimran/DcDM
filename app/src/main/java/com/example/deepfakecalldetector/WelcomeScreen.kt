package com.example.deepfakecalldetector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreenContent(onNavigateToLogin: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Light gray background
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo), // Replace with your app logo
                contentDescription = "App Logo",
                modifier = Modifier.size(160.dp)
            )

            // Welcome Text
            Text(
                text = "Welcome to",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = "DcDM",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            // Admin Login Button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { onNavigateToLogin("login/admin") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(22.dp)
            ) {
                Text(
                    text = "Admin Log in",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            // Employee Login Button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { onNavigateToLogin("login/employee") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(22.dp)
            ) {
                Text(
                    text = "Employee Log in",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            // Sign Up Button (Disabled Appearance)
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { onNavigateToLogin("signup") },
                enabled = true, // Enable sign up navigation
                shape = RoundedCornerShape(22.dp)
            ) {
                Text(
                    text = "Sign up",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            // Forgot Password
            TextButton(onClick = { onNavigateToLogin("forgotPassword") }) {
                Text(
                    text = "Forgot Password?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenContentPreview() {
    WelcomeScreenContent(onNavigateToLogin = { route ->
        println("Navigating to: $route")
    })
}
