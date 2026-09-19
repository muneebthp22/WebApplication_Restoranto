package com.example.clockworkriddle.data

import com.example.clockworkriddle.model.CogMilestone
import com.example.clockworkriddle.model.Puzzle
import com.example.clockworkriddle.model.PuzzleFamily

object PuzzleRepository {
    private val puzzles = mutableListOf<Puzzle>()
    private val cogMilestones = mutableListOf<CogMilestone>()

    init {
        initializePuzzles()
        initializeCogMilestones()
    }

    private fun initializePuzzles() {
        var id = 0
        val families = listOf(
            PuzzleFamily.SLIDING_PICTURE,
            PuzzleFamily.SLIDING_BLOCKS,
            PuzzleFamily.TANGRAM,
            PuzzleFamily.LIGHTS_OUT,
            PuzzleFamily.HANOI,
            PuzzleFamily.MAGIC_SQUARE,
            PuzzleFamily.SOLITAIRE,
            PuzzleFamily.UNTANGLE,
            PuzzleFamily.CIRCUIT,
            PuzzleFamily.RIDDLE
        )

        for (family in families) {
            for (level in 1..10) {
                puzzles.add(
                    Puzzle(
                        id = id++,
                        family = family,
                        difficulty = level,
                        title = "${family.name} Level $level",
                        data = generatePuzzleData(family, level)
                    )
                )
            }
        }

        puzzles.add(
            Puzzle(
                id = id,
                family = PuzzleFamily.RIDDLE,
                difficulty = 5,
                title = "The Final Puzzle",
                data = "final_puzzle_data"
            )
        )
    }

    private fun initializeCogMilestones() {
        for (i in 0..19) {
            cogMilestones.add(
                CogMilestone(
                    cogIndex = i,
                    requiredCompletions = (i + 1) * 5,
                    description = "Cog ${i + 1}"
                )
            )
        }
    }

    private fun generatePuzzleData(family: PuzzleFamily, difficulty: Int): String {
        return when (family) {
            PuzzleFamily.SLIDING_PICTURE -> {
                val size = 3 + difficulty / 3
                (1..size * size).shuffled().take(size * size - 1).plus(0).joinToString(",")
            }
            PuzzleFamily.LIGHTS_OUT -> {
                val size = 3
                val state = (0 until size * size).map { Math.random() > 0.5 }.toList()
                state.joinToString(",") { if (it) "1" else "0" }
            }
            else -> ""
        }
    }

    fun getPuzzles(): List<Puzzle> = puzzles
    fun getPuzzle(id: Int): Puzzle? = puzzles.find { it.id == id }
    fun getPuzzlesByFamily(family: PuzzleFamily): List<Puzzle> =
        puzzles.filter { it.family == family }
    fun getCogMilestones(): List<CogMilestone> = cogMilestones
}
