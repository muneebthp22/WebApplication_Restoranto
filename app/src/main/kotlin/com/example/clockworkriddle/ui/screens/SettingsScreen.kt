package com.example.clockworkriddle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clockworkriddle.model.GameState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    gameState: GameState,
    onMusicToggle: (Boolean) -> Unit,
    onSoundToggle: (Boolean) -> Unit,
    onHapticsToggle: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x1a1a1a))
    ) {
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0x2a2a2a)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SettingItem(
                label = "Music",
                isEnabled = gameState.musicEnabled,
                onToggle = onMusicToggle
            )

            SettingItem(
                label = "Sound Effects",
                isEnabled = gameState.soundEnabled,
                onToggle = onSoundToggle
            )

            SettingItem(
                label = "Haptics",
                isEnabled = gameState.hapticsEnabled,
                onToggle = onHapticsToggle
            )
        }
    }
}

@Composable
private fun SettingItem(
    label: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize = 18.sp,
            color = Color.White
        )
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            modifier = Modifier.size(width = 60.dp, height = 32.dp)
        )
    }
}
