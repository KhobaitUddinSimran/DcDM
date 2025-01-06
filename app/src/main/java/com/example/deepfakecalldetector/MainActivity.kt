package com.example.deepfakecalldetector

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.deepfakecalldetector.audio.NotificationHelper
import com.example.deepfakecalldetector.audio.sampleAudioList
import com.example.deepfakecalldetector.ui.theme.DeepfakeCallDetectorTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the notification channel
        NotificationHelper.createNotificationChannel(this)

        setContent {
            DeepfakeCallDetectorTheme {
                MainScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable

fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreenContent { route -> navController.navigate(route) }
        }
        composable("login/{userType}") { backStackEntry ->
            LoginScreen(navController, userType = backStackEntry.arguments?.getString("userType"))
        }
        composable("signup") {
            SignUpScreen(
                onSignUpComplete = { navController.navigate("dashboard") },
                onNavigateToLogin = { navController.navigate("login/admin") }
            )
        }
        composable("forgotPassword") {
            ForgotPasswordScreens { navController.popBackStack("login/admin", inclusive = false) }
        }
        composable("dashboard") {
            DashboardScreen(sampleAudioList)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DeepfakeCallDetectorTheme {
        MainScreen()
    }
}