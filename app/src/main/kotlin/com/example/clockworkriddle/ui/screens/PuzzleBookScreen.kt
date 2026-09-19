package com.example.clockworkriddle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun PuzzleBookScreen(
    puzzles: List<Puzzle>,
    completedPuzzles: Set<Int>,
    onPuzzleSelect: (Int) -> Unit,
    onBack: () -> Unit
) {
    var selectedFamily by remember { mutableStateOf<PuzzleFamily?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x1a1a1a))
    ) {
        TopAppBar(
            title = { Text("Puzzle Book") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0x2a2a2a)
            )
        )

        if (selectedFamily == null) {
            FamilySelector(
                families = PuzzleFamily.values().toList(),
                onFamilySelect = { selectedFamily = it }
            )
        } else {
            val familyPuzzles = puzzles.filter { it.family == selectedFamily }
            PuzzleGrid(
                puzzles = familyPuzzles,
                completedPuzzles = completedPuzzles,
                onPuzzleSelect = onPuzzleSelect,
                onBack = { selectedFamily = null }
            )
        }
    }
}

@Composable
private fun FamilySelector(
    families: List<PuzzleFamily>,
    onFamilySelect: (PuzzleFamily) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(families) { family ->
            FamilyCard(family) { onFamilySelect(family) }
        }
    }
}

@Composable
private fun FamilyCard(family: PuzzleFamily, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x8B7355)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                family.name.replace("_", " "),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun PuzzleGrid(
    puzzles: List<Puzzle>,
    completedPuzzles: Set<Int>,
    onPuzzleSelect: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(puzzles) { puzzle ->
                PuzzleCell(
                    puzzleId = puzzle.id,
                    isCompleted = puzzle.id in completedPuzzles,
                    onClick = { onPuzzleSelect(puzzle.id) }
                )
            }
        }
    }
}

@Composable
private fun PuzzleCell(
    puzzleId: Int,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .background(
                if (isCompleted) Color(0x4CAF50) else Color(0x8B7355)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            (puzzleId + 1).toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        if (isCompleted) {
            Text(
                "✓",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
            )
        }
    }
}
