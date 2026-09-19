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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "The Clockwork Riddle",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xD4AF37)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNewGame,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("New Game", fontSize = 18.sp)
            }

            if (gameState.currentProgress.isNotEmpty()) {
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x8B7355)
                    )
                ) {
                    Text("Continue", fontSize = 18.sp)
                }
            }

            Button(
                onClick = onPuzzleBook,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("Puzzle Book", fontSize = 18.sp)
            }

            Button(
                onClick = onSettings,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("Settings", fontSize = 18.sp)
            }

            Button(
                onClick = onCredits,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8B7355)
                )
            ) {
                Text("Credits", fontSize = 18.sp)
            }

            if (gameState.currentProgress.isNotEmpty()) {
                Button(
                    onClick = { showDeleteConfirm = true },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x663333)
                    )
                ) {
                    Text("Delete Save", fontSize = 18.sp)
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
