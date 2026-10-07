package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.MainViewModel
import com.example.ui.components.GamingButton
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentDetailScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onJoin: () -> Unit,
    onLeaderboardClick: (Long) -> Unit
) {
    val tournament by viewModel.selectedTournament.collectAsState()

    tournament?.let { t ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(t.name, color = White) },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Banner
                AsyncImage(
                    model = R.drawable.img_tournament_banner_1,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        t.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoItem("Prize Pool", "₹${t.prizePool}", NeonOrange)
                        InfoItem("Entry Fee", "₹${t.entryFee}", White)
                        InfoItem("Format", t.matchType, White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoItem("Date", t.date, White)
                        InfoItem("Time", t.time, White)
                        InfoItem("Map", t.map, White)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Rules", color = NeonOrange, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(t.rules, color = White.copy(alpha = 0.8f), modifier = Modifier.padding(top = 8.dp))

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Description", color = NeonOrange, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(t.description, color = White.copy(alpha = 0.8f), modifier = Modifier.padding(top = 8.dp))

                    Spacer(modifier = Modifier.height(32.dp))

                    GamingButton(
                        text = "View Leaderboard",
                        onClick = { onLeaderboardClick(t.id) },
                        containerColor = SurfaceColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (t.status == "Registration Open" && t.slotsFilled < t.totalSlots) {
                        GamingButton(
                            text = "Join Tournament",
                            onClick = onJoin
                        )
                    } else {
                        GamingButton(
                            text = if (t.slotsFilled >= t.totalSlots) "Full" else t.status,
                            onClick = {},
                            enabled = false,
                            containerColor = LightGray
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = White.copy(alpha = 0.6f), fontSize = 12.sp)
        Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
