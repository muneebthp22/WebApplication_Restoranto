package com.example.clockworkriddle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x1a1a1a))
    ) {
        TopAppBar(
            title = { Text("Credits") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CreditSection("Game Design", "Claude Code")
            CreditSection("Programming", "Claude Code")
            CreditSection("Puzzle Logic", "Claude Code")
            CreditSection("Art & UI", "Claude Code")
            CreditSection("Music & Sound", "Generated Synthetically")

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "The Clockwork Riddle v1.0.0",
                fontSize = 14.sp,
                color = Color(0xD4AF37),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                "A puzzle collection inspired by classic puzzle games.",
                fontSize = 12.sp,
                color = Color(0x999999),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun CreditSection(title: String, content: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xD4AF37)
        )
        Text(
            content,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}
