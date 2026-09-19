package com.example.clockworkriddle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clockworkriddle.model.GameState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    gameState: GameState,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onPuzzleBook: () -> Unit,
    onViewCogs: () -> Unit,
    onStartChallenge: () -> Unit,
    onSettings: () -> Unit,
    onCredits: () -> Unit,
    onDeleteSave: suspend () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x1a1a1a))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Text(
                "The Clockwork Riddle",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xD4AF37)
            )

            if (gameState.currentProgress.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x2a2a2a), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Progress", color = Color(0xB8860B), fontSize = 12.sp)
                        Text("${gameState.completedPuzzles.size}/100", color = Color(0xD4AF37), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Cogs", color = Color(0xB8860B), fontSize = 12.sp)
                        Text("${gameState.unlockedCogs.size}/20", color = Color(0xD4AF37), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNewGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("New Game", fontSize = 16.sp)
            }

            if (gameState.currentProgress.isNotEmpty()) {
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x8B7355)
                    )
                ) {
                    Text("Continue", fontSize = 16.sp)
                }
            }

            Button(
                onClick = onPuzzleBook,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("Puzzle Book", fontSize = 16.sp)
            }

            if (gameState.currentProgress.isNotEmpty()) {
                Button(
                    onClick = onViewCogs,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xB8860B)
                    )
                ) {
                    Text("Collected Cogs (${gameState.unlockedCogs.size})", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                if (gameState.completedPuzzles.size >= 95) {
                    Button(
                        onClick = onStartChallenge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x800020)
                        )
                    ) {
                        Text("The Final Challenge!", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Button(
                onClick = onSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("Settings", fontSize = 16.sp)
            }

            Button(
                onClick = onCredits,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("Credits", fontSize = 16.sp)
            }

            if (gameState.currentProgress.isNotEmpty()) {
                Button(
                    onClick = { showDeleteConfirm = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x663333)
                    )
                ) {
                    Text("Delete Save", fontSize = 16.sp)
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete saved progress?") },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            onDeleteSave()
                        }
                        showDeleteConfirm = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirm = false }) {
                    Text("No")
                }
            }
        )
    }
}
