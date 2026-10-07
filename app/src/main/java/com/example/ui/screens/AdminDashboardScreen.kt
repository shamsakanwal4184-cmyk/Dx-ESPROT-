package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.components.GamingButton
import com.example.ui.components.GamingCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: MainViewModel,
    onManageTournaments: () -> Unit,
    onRegistrations: () -> Unit,
    onLogout: () -> Unit
) {
    val tournaments by viewModel.tournaments.collectAsState()
    val registrations by viewModel.allRegistrations.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ADMIN DASHBOARD", color = White) },
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
            item {
                Text("Statistics", color = NeonOrange, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    StatCard("Total Tournaments", tournaments.size.toString(), Icons.Default.EmojiEvents, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    StatCard("Total Regs", registrations.size.toString(), Icons.Default.People, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    val active = tournaments.count { it.status == "Registration Open" || it.status == "Live" }
                    StatCard("Active", active.toString(), Icons.Default.FlashOn, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    val pending = registrations.count { it.regStatus == "Pending" }
                    StatCard("Pending Regs", pending.toString(), Icons.Default.PendingActions, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Actions", color = NeonOrange, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                AdminActionCard("Manage Tournaments", "Create, Edit, Delete tournaments", Icons.Default.Edit, onManageTournaments)
                Spacer(modifier = Modifier.height(8.dp))
                AdminActionCard("Manage Registrations", "Approve or Reject players/teams", Icons.Default.HowToReg, onRegistrations)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    GamingCard(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Icon(icon, contentDescription = null, tint = NeonOrange, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(label, color = White.copy(alpha = 0.6f), fontSize = 12.sp)
        }
    }
}

@Composable
fun AdminActionCard(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    GamingCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = NeonOrange, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(subtitle, color = White.copy(alpha = 0.6f), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = White.copy(alpha = 0.3f))
        }
    }
}
