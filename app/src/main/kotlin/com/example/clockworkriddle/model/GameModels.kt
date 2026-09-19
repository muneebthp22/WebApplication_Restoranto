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
    // Tier A - Foundation
    RIDDLES, NUMBER_SQUARES, SLIDING_BLOCKS, LIGHTS_OUT, SLIDING_PICTURE,

    // Tier B - Development
    TIMING_GAUGES, PATTERN_MATCHING, BULB_MEMORY, ROTATING_PICTURE, HANOI,

    // Tier C - Expansion
    LINE_PARTITION, CIRCUIT_PATHS, UNTANGLE, SOLITAIRE, LIQUID_JARS,

    // Tier D - Mastery
    DOMINO_PLACEMENT, GEAR_TRAIN, CODE_LOCK, ONE_STROKE_PATH, TANGRAM;

    fun displayName(): String = when (this) {
        RIDDLES -> "Riddles"
        NUMBER_SQUARES -> "Number Squares"
        SLIDING_BLOCKS -> "Sliding Blocks"
        LIGHTS_OUT -> "Lights Out"
        SLIDING_PICTURE -> "Sliding Picture"
        TIMING_GAUGES -> "Timing Gauges"
        PATTERN_MATCHING -> "Pattern Matching"
        BULB_MEMORY -> "Bulb Memory"
        ROTATING_PICTURE -> "Rotating Picture"
        HANOI -> "Tower of Hanoi"
        LINE_PARTITION -> "Line Partition"
        CIRCUIT_PATHS -> "Circuit Paths"
        UNTANGLE -> "Untangle"
        SOLITAIRE -> "Peg Solitaire"
        LIQUID_JARS -> "Liquid Jars"
        DOMINO_PLACEMENT -> "Domino Placement"
        GEAR_TRAIN -> "Gear Train"
        CODE_LOCK -> "Code Lock"
        ONE_STROKE_PATH -> "One Stroke Path"
        TANGRAM -> "Tangram"
    }
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
