package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.Registration
import com.example.ui.MainViewModel
import com.example.ui.components.GamingButton
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onSuccess: (Long) -> Unit
) {
    val tournament by viewModel.selectedTournament.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    var playerName by remember { mutableStateOf(user?.name ?: "") }
    var ffUid by remember { mutableStateOf("") }
    var ign by remember { mutableStateOf("") }
    var whatsapp by remember { mutableStateOf(user?.mobile ?: "") }
    var teamName by remember { mutableStateOf("") }
    var teamMembers by remember { mutableStateOf("") }
    var paymentRef by remember { mutableStateOf("") }
    
    var error by remember { mutableStateOf<String?>(null) }

    tournament?.let { t ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("JOIN TOURNAMENT", color = White) },
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    t.name,
                    color = NeonOrange,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = playerName,
                    onValueChange = { playerName = it },
                    label = { Text("Player Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ffUid,
                    onValueChange = { ffUid = it },
                    label = { Text("Free Fire UID") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ign,
                    onValueChange = { ign = it },
                    label = { Text("In-Game Name (IGN)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = whatsapp,
                    onValueChange = { whatsapp = it },
                    label = { Text("WhatsApp Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )

                if (t.matchType != "Solo") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Team Information", color = White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = teamName,
                        onValueChange = { teamName = it },
                        label = { Text("Team Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = White,
                            focusedTextColor = White,
                            unfocusedBorderColor = White.copy(alpha = 0.3f),
                            focusedBorderColor = NeonOrange
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = teamMembers,
                        onValueChange = { teamMembers = it },
                        label = { Text("Team Members (Names & UIDs)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = White,
                            focusedTextColor = White,
                            unfocusedBorderColor = White.copy(alpha = 0.3f),
                            focusedBorderColor = NeonOrange
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Payment Information", color = White, fontWeight = FontWeight.Bold)
                Text("Entry Fee: ₹${t.entryFee}", color = NeonOrange, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = paymentRef,
                    onValueChange = { paymentRef = it },
                    label = { Text("Payment Reference ID / Screenshot ID") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                error?.let {
                    Text(it, color = NeonRed, modifier = Modifier.padding(bottom = 16.dp))
                }

                GamingButton(
                    text = "Submit Registration",
                    onClick = {
                        if (ffUid.isEmpty() || ign.isEmpty() || whatsapp.isEmpty()) {
                            error = "Please fill all required fields"
                        } else {
                            val reg = Registration(
                                userId = user?.id ?: 0,
                                tournamentId = t.id,
                                playerName = playerName,
                                ffUid = ffUid,
                                ign = ign,
                                whatsapp = whatsapp,
                                teamName = teamName,
                                teamMembers = teamMembers,
                                paymentRef = paymentRef
                            )
                            viewModel.registerForTournament(reg) { regId ->
                                onSuccess(regId)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
