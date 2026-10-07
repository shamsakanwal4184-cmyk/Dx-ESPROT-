package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.components.GamingCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val tournament by viewModel.selectedTournament.collectAsState()
    // In real app, we'd fetch results for the selected tournament
    // For now, let's show empty state or sample results if we had any
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LEADERBOARD", color = White) },
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
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            tournament?.let { t ->
                Text(t.name, color = NeonOrange, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth().background(SurfaceColor).padding(8.dp)) {
                    Text("#", modifier = Modifier.width(30.dp), color = White, fontWeight = FontWeight.Bold)
                    Text("TEAM", modifier = Modifier.weight(1f), color = White, fontWeight = FontWeight.Bold)
                    Text("KILLS", modifier = Modifier.width(50.dp), color = White, fontWeight = FontWeight.Bold)
                    Text("PTS", modifier = Modifier.width(50.dp), color = White, fontWeight = FontWeight.Bold)
                }
                
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "Results will be updated after the match ends.",
                        modifier = Modifier.align(Alignment.Center),
                        color = White.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
