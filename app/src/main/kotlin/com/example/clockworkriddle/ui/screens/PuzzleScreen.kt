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
            PuzzleFamily.SLIDING_PICTURE -> SlidingPictureView(puzzleData = puzzle.data)
            PuzzleFamily.SLIDING_BLOCKS -> SlidingBlocksView(puzzleData = puzzle.data)
            PuzzleFamily.LIGHTS_OUT -> LightsOutView(puzzleData = puzzle.data)
            PuzzleFamily.TANGRAM -> TangramView(puzzleData = puzzle.data)
            PuzzleFamily.HANOI -> HanoiView(difficulty = puzzle.difficulty)
            PuzzleFamily.NUMBER_SQUARES -> NumberSquaresView(puzzleData = puzzle.data)
            PuzzleFamily.RIDDLES -> RiddlesView(puzzleData = puzzle.data)
            PuzzleFamily.SOLITAIRE -> SolitaireView(puzzleData = puzzle.data)
            PuzzleFamily.UNTANGLE -> UntangleView(puzzleData = puzzle.data)
            PuzzleFamily.CIRCUIT_PATHS -> CircuitView(puzzleData = puzzle.data)
            PuzzleFamily.TIMING_GAUGES -> TimingGaugesView(puzzleData = puzzle.data)
            PuzzleFamily.PATTERN_MATCHING -> PatternMatchingView(puzzleData = puzzle.data)
            PuzzleFamily.BULB_MEMORY -> BulbMemoryView(puzzleData = puzzle.data)
            PuzzleFamily.ROTATING_PICTURE -> RotatingPictureView(puzzleData = puzzle.data)
            PuzzleFamily.LINE_PARTITION -> LinePartitionView(puzzleData = puzzle.data)
            PuzzleFamily.LIQUID_JARS -> LiquidJarsView(puzzleData = puzzle.data)
            PuzzleFamily.DOMINO_PLACEMENT -> DominoPlacementView(puzzleData = puzzle.data)
            PuzzleFamily.GEAR_TRAIN -> GearTrainView(puzzleData = puzzle.data)
            PuzzleFamily.CODE_LOCK -> CodeLockView(puzzleData = puzzle.data)
            PuzzleFamily.ONE_STROKE_PATH -> OneStrokePathView(puzzleData = puzzle.data)
        }
    }
}

@Composable
fun SlidingPictureView(puzzleData: String) {
    Text("Sliding Picture Puzzle", color = Color.White)
}

@Composable
fun SlidingBlocksView(puzzleData: String) {
    Text("Sliding Blocks Puzzle", color = Color.White)
}

@Composable
fun LightsOutView(puzzleData: String) {
    Text("Lights Out", color = Color.White)
}

@Composable
fun TangramView(puzzleData: String) {
    Text("Tangram - ${puzzleData.takeIf { it.isNotEmpty() } ?: "Shape Assembly"}", color = Color.White)
}

@Composable
fun HanoiView(difficulty: Int) {
    Text("Tower of Hanoi - $difficulty Disks", color = Color.White)
}

@Composable
fun NumberSquaresView(puzzleData: String) {
    Text("Number Squares", color = Color.White)
}

@Composable
fun RiddlesView(puzzleData: String) {
    Text("Riddle Challenge", color = Color.White)
}

@Composable
fun SolitaireView(puzzleData: String) {
    Text("Peg Solitaire", color = Color.White)
}

@Composable
fun UntangleView(puzzleData: String) {
    Text("Untangle Lines", color = Color.White)
}

@Composable
fun CircuitView(puzzleData: String) {
    Text("Circuit Paths", color = Color.White)
}

@Composable
fun TimingGaugesView(puzzleData: String) {
    Text("Timing Gauges", color = Color.White)
}

@Composable
fun PatternMatchingView(puzzleData: String) {
    Text("Pattern Matching", color = Color.White)
}

@Composable
fun BulbMemoryView(puzzleData: String) {
    Text("Bulb Memory", color = Color.White)
}

@Composable
fun RotatingPictureView(puzzleData: String) {
    Text("Rotating Picture", color = Color.White)
}

@Composable
fun LinePartitionView(puzzleData: String) {
    Text("Line Partition", color = Color.White)
}

@Composable
fun LiquidJarsView(puzzleData: String) {
    Text("Liquid Jars", color = Color.White)
}

@Composable
fun DominoPlacementView(puzzleData: String) {
    Text("Domino Placement", color = Color.White)
}

@Composable
fun GearTrainView(puzzleData: String) {
    Text("Gear Train", color = Color.White)
}

@Composable
fun CodeLockView(puzzleData: String) {
    Text("Code Lock", color = Color.White)
}

@Composable
fun OneStrokePathView(puzzleData: String) {
    Text("One Stroke Path", color = Color.White)
}
