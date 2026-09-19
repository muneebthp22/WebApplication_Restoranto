package com.example.clockworkriddle.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.example.clockworkriddle.model.FinalChallengeStage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalChallengeScreen(
    currentStage: Int,
    completedStages: Set<Int>,
    onStageStart: (Int) -> Unit,
    onBack: () -> Unit
) {
    val stages = StorySystem.getFinalChallenge()
    val currentStageData = stages.getOrNull(currentStage - 1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x0a0a0a))
    ) {
        TopAppBar(
            title = {
                Text(
                    "The Final Challenge - Stage $currentStage of ${stages.size}",
                    color = Color(0xD4AF37)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0x1a1a1a)
            )
        )

        if (currentStageData == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Challenge Complete!",
                    color = Color(0x4CAF50),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "All five stages conquered. The mechanism is restored.",
                    color = Color(0xD4AF37),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xB8860B)
                    )
                ) {
                    Text("Return to Menu", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }

                items(
                    count = stages.size,
                    key = { it },
                    itemContent = { index ->
                        val stage = stages[index]
                        val isCompleted = index + 1 in completedStages
                        val isCurrent = index + 1 == currentStage

                        StageCard(
                            stage = stage,
                            isCompleted = isCompleted,
                            isCurrent = isCurrent,
                            onSelect = { if (isCurrent) onStageStart(stage.stageId) }
                        )
                    }
                )

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StageCard(
    stage: FinalChallengeStage,
    isCompleted: Boolean,
    isCurrent: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when {
                    isCompleted -> Color(0x1a3a1a)
                    isCurrent -> Color(0x3a3a1a)
                    else -> Color(0x2a2a2a)
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCompleted -> Color(0x1a3a1a)
                isCurrent -> Color(0x3a3a1a)
                else -> Color(0x2a2a2a)
            }
        ),
        onClick = if (isCurrent) {{ onSelect() }} else { {} }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = when {
                                isCompleted -> Color(0x4CAF50)
                                isCurrent -> Color(0xB8860B)
                                else -> Color(0x666666)
                            },
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Text("✓", color = Color(0x0a0a0a), fontWeight = FontWeight.Bold)
                    } else {
                        Text("${stage.stageId}", color = Color(0x0a0a0a), fontWeight = FontWeight.Bold)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stage.description,
                        color = Color(0xD4AF37),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        "Family: ${stage.puzzleFamily.displayName()}",
                        color = Color(0xB8860B),
                        fontSize = 12.sp
                    )
                }
            }

            Divider(color = Color(0x444444), modifier = Modifier.fillMaxWidth())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0x1a1a1a),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFBF00),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    "Reward: ${stage.reward}",
                    color = Color(0xFFBF00),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isCurrent && !isCompleted) {
                Button(
                    onClick = onSelect,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xB8860B)
                    )
                ) {
                    Text("Start Stage", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
