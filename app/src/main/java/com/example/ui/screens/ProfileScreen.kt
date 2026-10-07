package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.components.GamingCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()
    val registrations by viewModel.userRegistrations.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PROFILE", color = White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = NeonRed)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        containerColor = BackgroundColor
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            user?.let { u ->
                item {
                    GamingCard {
                        Column {
                            Text(u.name, color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("@${u.username}", color = NeonOrange, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(u.email, color = White.copy(alpha = 0.6f), fontSize = 12.sp)
                            Text(u.mobile, color = White.copy(alpha = 0.6f), fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("My Registrations", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(registrations) { reg ->
                    GamingCard(modifier = Modifier.padding(bottom = 8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Tournament ID: ${reg.tournamentId}", color = White, fontWeight = FontWeight.Bold)
                                Text("Status: ${reg.regStatus}", color = when(reg.regStatus) {
                                    "Approved" -> Color.Green
                                    "Rejected" -> NeonRed
                                    else -> Color.Yellow
                                }, fontSize = 12.sp)
                            }
                        }
                    }
                }
                
                if (registrations.isEmpty()) {
                    item {
                        Text("You haven't joined any tournaments yet.", color = White.copy(alpha = 0.5f), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
