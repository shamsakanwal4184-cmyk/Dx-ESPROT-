package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.entities.Tournament
import com.example.ui.theme.*

@Composable
fun GamingCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(
                1.dp,
                Brush.linearGradient(listOf(NeonOrange.copy(alpha = 0.5f), Color.Transparent)),
                RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun TournamentCard(
    tournament: Tournament,
    onClick: () -> Unit
) {
    GamingCard(
        modifier = Modifier.padding(bottom = 16.dp),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Placeholder for banner
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LightGray)
            ) {
                // In real app we'd load the image
                Text(
                    text = "FF",
                    color = NeonOrange,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = tournament.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Prize: ₹${tournament.prizePool}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeonOrange
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Entry: ₹${tournament.entryFee}",
                        style = MaterialTheme.typography.bodySmall,
                        color = White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "${tournament.slotsFilled}/${tournament.totalSlots} Slots",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (tournament.slotsFilled >= tournament.totalSlots) NeonRed else NeonOrange
                    )
                }
                
                Badge(
                    containerColor = when(tournament.status) {
                        "Registration Open" -> Color(0xFF4CAF50)
                        "Full" -> Color(0xFFFF9800)
                        "Live" -> NeonRed
                        else -> LightGray
                    },
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(tournament.status, color = White, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun GamingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = NeonOrange,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, White.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = White,
            disabledContainerColor = LightGray
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(
            text = text.uppercase(),
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}
