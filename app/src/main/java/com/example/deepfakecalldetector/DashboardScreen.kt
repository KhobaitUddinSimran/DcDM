package com.example.deepfakecalldetector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.deepfakecalldetector.audio.DetectedAudio
import com.example.deepfakecalldetector.audio.sampleAudioList

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(detectedAudios: List<DetectedAudio>) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var isDeepfake by remember { mutableStateOf(false) }

    // BroadcastReceiver to update UI on deepfake detection
    val deepfakeReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                isDeepfake = intent.getBooleanExtra("isDeepfake", false)
            }
        }
    }

    DisposableEffect(Unit) {
        val filter = IntentFilter("com.example.deepfakecalldetector.DEEPFAKE_DETECTED")
        context.registerReceiver(deepfakeReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        onDispose {
            context.unregisterReceiver(deepfakeReceiver)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            DrawerContent { coroutineScope.launch { drawerState.close() } }
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Notification action */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )

            var selectedTab by remember { mutableStateOf(0) }
            val tabs = listOf("Recent", "Suspected")

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            val filteredAudios = if (selectedTab == 1) {
                detectedAudios.filter { it.status == "Suspected Deepfake" }
            } else detectedAudios

            LazyColumn(Modifier.fillMaxSize()) {
                items(filteredAudios) { audio ->
                    AudioCard(audio)
                }
            }
        }
    }
}

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Menu",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        DrawerItem("Settings", Icons.Default.Settings, onCloseDrawer)
        DrawerItem("Logout", Icons.AutoMirrored.Filled.ExitToApp, onCloseDrawer)
    }
}

@Composable
fun DrawerItem(title: String, icon: ImageVector, onCloseDrawer: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onCloseDrawer() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title)
        Spacer(Modifier.width(12.dp))
        Text(title)
    }
}

@Composable
fun AudioCard(audio: DetectedAudio) {
    val cardColor = when (audio.status) {
        "Suspected Deepfake" -> Color(0xFFFFE0E0) // Light Red
        else -> Color.White
    }

    val statusColor = when (audio.status) {
        "In Progress" -> Color(0xFFB3CFFF) // Light Blue
        "Verified" -> Color(0xFFBFFFB3) // Light Green
        "Suspected Deepfake" -> Color(0xFFFFB3B3) // Red
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(2.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = audio.phoneNumber, fontWeight = FontWeight.Bold)
                StatusBadge(status = audio.status, color = statusColor)
            }
            Text(text = audio.date, color = Color.Gray)
        }
    }
}

@Composable
fun StatusBadge(status: String, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 4.dp)
            .background(color, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = status, color = Color.White, fontSize = 12.sp)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(detectedAudios = sampleAudioList)
}