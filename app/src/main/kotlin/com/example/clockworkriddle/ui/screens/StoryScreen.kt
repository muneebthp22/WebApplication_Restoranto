package com.example.clockworkriddle.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clockworkriddle.data.StorySystem
import com.example.clockworkriddle.model.StoryScene
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    sceneId: String,
    onContinue: () -> Unit,
    onClose: () -> Unit
) {
    val scene = StorySystem.getScene(sceneId)

    if (scene == null) {
        onClose()
        return
    }

    var showText by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        showText = true
        delay(1500)
        showButton = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x0a0a0a))
    ) {
        TopAppBar(
            title = { Text(scene.title, color = Color(0xD4AF37)) },
            navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xD4AF37))
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0x1a1a1a)
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0x2a2a2a)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                scene.imageDescription,
                color = Color(0xB8860B),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "— ${scene.narrator} —",
                color = Color(0xD4AF37),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(
                visible = showText,
                enter = fadeIn()
            ) {
                Text(
                    scene.text,
                    color = Color(0xE0D7BC),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Justify
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(
                visible = showButton,
                enter = slideInVertically(initialOffsetY = { it })
            ) {
                Button(
                    onClick = {
                        onContinue()
                        onClose()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xB8860B)
                    )
                ) {
                    Text("Continue", color = Color(0x0a0a0a), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CogCollectionScreen(
    collectedCogs: Set<Int>,
    onBack: () -> Unit
) {
    val cogs = StorySystem.getCogs()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x0a0a0a))
    ) {
        TopAppBar(
            title = {
                Text(
                    "Collected Cogs: ${collectedCogs.size}/20",
                    color = Color(0xD4AF37)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.Close, contentDescription = "Back", tint = Color(0xD4AF37))
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0x1a1a1a)
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = cogs.size,
                key = { it },
                itemContent = { index ->
                val cog = cogs[index]
                val isCollected = index in collectedCogs

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCollected)
                            Color(0x2a2a2a)
                        else
                            Color(0x1a1a1a)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (isCollected)
                                        Color(0xB8860B)
                                    else
                                        Color(0x444444),
                                    shape = androidx.compose.foundation.shape.CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${index + 1}",
                                color = if (isCollected)
                                    Color(0x0a0a0a)
                                else
                                    Color(0x888888),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                cog.name,
                                color = Color(0xD4AF37),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                cog.description,
                                color = if (isCollected)
                                    Color(0xB8860B)
                                else
                                    Color(0x666666),
                                fontSize = 12.sp
                            )
                        }

                        if (isCollected) {
                            Text(
                                "✓",
                                color = Color(0x4CAF50),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                }
            )
        }
    }
}
