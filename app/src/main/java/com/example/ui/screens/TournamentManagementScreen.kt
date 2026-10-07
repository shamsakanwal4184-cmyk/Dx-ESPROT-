package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.Tournament
import com.example.ui.MainViewModel
import com.example.ui.components.GamingButton
import com.example.ui.components.GamingCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentManagementScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val tournaments by viewModel.tournaments.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MANAGE TOURNAMENTS", color = White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = NeonOrange,
                contentColor = White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = BackgroundColor
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(tournaments) { t ->
                GamingCard(modifier = Modifier.padding(bottom = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(t.name, color = White, fontWeight = FontWeight.Bold)
                            Text("Status: ${t.status}", color = NeonOrange, fontSize = 12.sp)
                        }
                        IconButton(onClick = { viewModel.deleteTournament(t) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = NeonRed)
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddTournamentDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { tournament ->
                    viewModel.createTournament(tournament)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTournamentDialog(
    onDismiss: () -> Unit,
    onConfirm: (Tournament) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var entryFee by remember { mutableStateOf("10") }
    var prizePool by remember { mutableStateOf("1000") }
    var matchType by remember { mutableStateOf("Squad") }
    var date by remember { mutableStateOf("2026-10-10") }
    var time by remember { mutableStateOf("18:00") }
    var totalSlots by remember { mutableStateOf("48") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Tournament", color = White) },
        text = {
            Column(modifier = Modifier.verticalScroll(androidx.compose.foundation.rememberScrollState())) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = entryFee, onValueChange = { entryFee = it }, label = { Text("Entry Fee") })
                OutlinedTextField(value = prizePool, onValueChange = { prizePool = it }, label = { Text("Prize Pool") })
                OutlinedTextField(value = matchType, onValueChange = { matchType = it }, label = { Text("Format (Solo/Squad)") })
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date") })
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time") })
                OutlinedTextField(value = totalSlots, onValueChange = { totalSlots = it }, label = { Text("Slots") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    Tournament(
                        name = name,
                        entryFee = entryFee.toIntOrNull() ?: 0,
                        prizePool = prizePool.toIntOrNull() ?: 0,
                        matchType = matchType,
                        date = date,
                        time = time,
                        totalSlots = totalSlots.toIntOrNull() ?: 48,
                        status = "Registration Open",
                        rules = "1. Play fair\n2. No hacks\n3. Respect others",
                        description = "Professional Free Fire tournament by DX ESPORTS."
                    )
                )
            }) {
                Text("Create", color = NeonOrange)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = White)
            }
        },
        containerColor = SurfaceColor
    )
}
