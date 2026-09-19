package com.example.clockworkriddle.model

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val completedPuzzles: Set<Int> = emptySet(),
    val unlockedCogs: Set<Int> = emptySet(),
    val currentProgress: Map<Int, PuzzleProgress> = emptyMap(),
    val soundEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true
)

@Serializable
data class PuzzleProgress(
    val puzzleId: Int,
    val state: String,
    val hintsUsed: Int = 0,
    val solved: Boolean = false
)

@Serializable
data class Puzzle(
    val id: Int,
    val family: PuzzleFamily,
    val difficulty: Int,
    val title: String,
    val data: String
)

enum class PuzzleFamily {
    SLIDING_PICTURE, SLIDING_BLOCKS, TANGRAM, LIGHTS_OUT,
    HANOI, MAGIC_SQUARE, SOLITAIRE, UNTANGLE,
    CIRCUIT, RIDDLE
}

data class CogMilestone(
    val cogIndex: Int,
    val requiredCompletions: Int,
    val description: String
)

sealed class PuzzleMove {
    data class SlidingMove(val fromRow: Int, val fromCol: Int) : PuzzleMove()
    data class TangramMove(val pieceId: Int, val rotation: Int, val x: Float, val y: Float) : PuzzleMove()
    data class LightsOutToggle(val row: Int, val col: Int) : PuzzleMove()
    data class HanoiMove(val fromPeg: Int, val toPeg: Int) : PuzzleMove()
    data class RotationMove(val itemId: Int, val rotation: Int) : PuzzleMove()
    data class UntangleMove(val vertexId: Int, val x: Float, val y: Float) : PuzzleMove()
}
