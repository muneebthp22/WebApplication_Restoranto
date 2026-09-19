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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clockworkriddle.model.Puzzle
import com.example.clockworkriddle.model.PuzzleFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleScreen(
    puzzle: Puzzle,
    isSolved: Boolean,
    onHint: () -> Unit,
    onReset: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var showHint by remember { mutableStateOf(false) }
    var currentHint by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x1a1a1a))
    ) {
        TopAppBar(
            title = { Text("Puzzle ${puzzle.id + 1}") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                puzzle.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xD4AF37)
            )

            PuzzleView(puzzle = puzzle)

            if (isSolved) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x2d5016)
                    )
                ) {
                    Text(
                        "✓ Puzzle Solved!",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0x4CAF50)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        currentHint = "Think carefully..."
                        showHint = true
                        onHint()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Hint")
                }

                Button(
                    onClick = onReset,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x663333)
                    )
                ) {
                    Text("Reset")
                }
            }

            if (isSolved) {
                Button(
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x4CAF50)
                    )
                ) {
                    Text("Next Puzzle")
                }
            }

            if (showHint) {
                AlertDialog(
                    onDismissRequest = { showHint = false },
                    title = { Text("Hint") },
                    text = { Text(currentHint) },
                    confirmButton = {
                        Button(onClick = { showHint = false }) {
                            Text("Got it")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PuzzleView(puzzle: Puzzle) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color(0x2a2a2a)),
        contentAlignment = Alignment.Center
    ) {
        when (puzzle.family) {
            PuzzleFamily.SLIDING_PICTURE,
            PuzzleFamily.SLIDING_BLOCKS -> {
                SlidingPuzzleView(puzzleData = puzzle.data)
            }
            PuzzleFamily.LIGHTS_OUT -> {
                LightsOutView(puzzleData = puzzle.data)
            }
            PuzzleFamily.TANGRAM -> {
                TangramView()
            }
            PuzzleFamily.HANOI -> {
                HanoiView()
            }
            PuzzleFamily.MAGIC_SQUARE -> {
                MagicSquareView()
            }
            PuzzleFamily.SOLITAIRE -> {
                SolitaireView()
            }
            PuzzleFamily.UNTANGLE -> {
                UntangleView()
            }
            PuzzleFamily.CIRCUIT -> {
                CircuitView()
            }
            PuzzleFamily.RIDDLE -> {
                RiddleView()
            }
        }
    }
}

@Composable
fun SlidingPuzzleView(puzzleData: String) {
    Text("Sliding Puzzle", color = Color.White)
}

@Composable
fun LightsOutView(puzzleData: String) {
    Text("Lights Out", color = Color.White)
}

@Composable
fun TangramView() {
    Text("Tangram", color = Color.White)
}

@Composable
fun HanoiView() {
    Text("Tower of Hanoi", color = Color.White)
}

@Composable
fun MagicSquareView() {
    Text("Magic Square", color = Color.White)
}

@Composable
fun SolitaireView() {
    Text("Solitaire", color = Color.White)
}

@Composable
fun UntangleView() {
    Text("Untangle", color = Color.White)
}

@Composable
fun CircuitView() {
    Text("Circuit", color = Color.White)
}

@Composable
fun RiddleView() {
    Text("Riddle", color = Color.White)
}
