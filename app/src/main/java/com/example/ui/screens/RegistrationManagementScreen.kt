package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.Registration
import com.example.ui.MainViewModel
import com.example.ui.components.GamingCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationManagementScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val registrations by viewModel.allRegistrations.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MANAGE REGISTRATIONS", color = White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
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
            items(registrations) { reg ->
                RegistrationItem(
                    registration = reg,
                    onApprove = { viewModel.updateRegistrationStatus(reg, "Approved") },
                    onReject = { viewModel.updateRegistrationStatus(reg, "Rejected") }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (registrations.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                        Text("No registrations yet", modifier = Modifier.align(Alignment.Center), color = White.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun RegistrationItem(
    registration: Registration,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    GamingCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(registration.playerName, color = White, fontWeight = FontWeight.Bold)
                Text("IGN: ${registration.ign}", color = White.copy(alpha = 0.8f), fontSize = 12.sp)
                Text("UID: ${registration.ffUid}", color = White.copy(alpha = 0.8f), fontSize = 12.sp)
                if (!registration.teamName.isNullOrEmpty()) {
                    Text("Team: ${registration.teamName}", color = NeonOrange, fontSize = 12.sp)
                }
                Text("Status: ${registration.regStatus}", color = when(registration.regStatus) {
                    "Approved" -> Color.Green
                    "Rejected" -> NeonRed
                    else -> Color.Yellow
                }, fontSize = 12.sp)
            }

            if (registration.regStatus == "Pending") {
                IconButton(onClick = onApprove) {
                    Icon(Icons.Default.Check, contentDescription = "Approve", tint = Color.Green)
                }
                IconButton(onClick = onReject) {
                    Icon(Icons.Default.Close, contentDescription = "Reject", tint = NeonRed)
                }
            }
        }
    }
}
