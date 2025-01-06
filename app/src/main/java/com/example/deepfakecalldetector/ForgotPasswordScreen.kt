package com.example.deepfakecalldetector

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreens(onPasswordReset: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Forgot Password?", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        var email by remember { mutableStateOf("") }
        TextField(value = email, onValueChange = { email = it }, label = { Text("Enter your email") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onPasswordReset, modifier = Modifier.fillMaxWidth()) {
            Text("Reset Password")
        }
    }
}




