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
            PuzzleFamily.RIDDLES, PuzzleFamily.NUMBER_SQUARES, PuzzleFamily.SLIDING_BLOCKS,
            PuzzleFamily.LIGHTS_OUT, PuzzleFamily.SLIDING_PICTURE,
            PuzzleFamily.TIMING_GAUGES, PuzzleFamily.PATTERN_MATCHING, PuzzleFamily.BULB_MEMORY,
            PuzzleFamily.ROTATING_PICTURE, PuzzleFamily.HANOI,
            PuzzleFamily.LINE_PARTITION, PuzzleFamily.CIRCUIT_PATHS, PuzzleFamily.UNTANGLE,
            PuzzleFamily.SOLITAIRE, PuzzleFamily.LIQUID_JARS,
            PuzzleFamily.DOMINO_PLACEMENT, PuzzleFamily.GEAR_TRAIN, PuzzleFamily.CODE_LOCK,
            PuzzleFamily.ONE_STROKE_PATH, PuzzleFamily.TANGRAM
        )

        for (family in families) {
            for (level in 1..5) {
                puzzles.add(
                    Puzzle(
                        id = id++,
                        family = family,
                        difficulty = level,
                        title = "${family.displayName()} - Level $level",
                        data = generatePuzzleData(family, level)
                    )
                )
            }
        }
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
                val size = 3 + (difficulty - 1) / 2
                (1..size * size).shuffled().take(size * size - 1).plus(0).joinToString(",")
            }
            PuzzleFamily.SLIDING_BLOCKS -> {
                generateSlidingBlocksData(difficulty)
            }
            PuzzleFamily.LIGHTS_OUT -> {
                val size = 3 + (difficulty - 1) / 2
                (0 until size * size).map { Math.random() > 0.5 }.joinToString(",") { if (it) "1" else "0" }
            }
            PuzzleFamily.NUMBER_SQUARES -> {
                val size = 3 + (difficulty - 1) / 2
                (1..size * size).shuffled().joinToString(",")
            }
            PuzzleFamily.RIDDLES -> {
                generateRiddleData(difficulty)
            }
            PuzzleFamily.HANOI -> {
                (3 + difficulty).toString()
            }
            PuzzleFamily.TANGRAM -> {
                generateTangramData(difficulty)
            }
            PuzzleFamily.UNTANGLE -> {
                generateUntangleData(difficulty)
            }
            PuzzleFamily.CIRCUIT_PATHS -> {
                generateCircuitData(difficulty)
            }
            PuzzleFamily.SOLITAIRE -> {
                generateSolitaireData(difficulty)
            }
            PuzzleFamily.TIMING_GAUGES -> {
                generateTimingGaugesData(difficulty)
            }
            PuzzleFamily.PATTERN_MATCHING -> {
                generatePatternMatchingData(difficulty)
            }
            PuzzleFamily.BULB_MEMORY -> {
                generateBulbMemoryData(difficulty)
            }
            PuzzleFamily.ROTATING_PICTURE -> {
                generateRotatingPictureData(difficulty)
            }
            PuzzleFamily.LINE_PARTITION -> {
                generateLinePartitionData(difficulty)
            }
            PuzzleFamily.LIQUID_JARS -> {
                generateLiquidJarsData(difficulty)
            }
            PuzzleFamily.DOMINO_PLACEMENT -> {
                generateDominoData(difficulty)
            }
            PuzzleFamily.GEAR_TRAIN -> {
                generateGearTrainData(difficulty)
            }
            PuzzleFamily.CODE_LOCK -> {
                generateCodeLockData(difficulty)
            }
            PuzzleFamily.ONE_STROKE_PATH -> {
                generateOneStrokePathData(difficulty)
            }
        }
    }

    private fun generateSlidingBlocksData(difficulty: Int): String {
        val blockCount = 4 + difficulty
        return (1..blockCount).map { "${it}:${(it - 1) % 4},${(it - 1) / 4}" }.joinToString("|")
    }

    private fun generateRiddleData(difficulty: Int): String {
        val riddles = listOf(
            "I speak without a mouth and hear without ears. I have no body, but I come alive with wind. What am I?|echo",
            "What can run but never walks, has a mouth but never talks, has a bed but never sleeps?|river",
            "I have cities but no houses, forests but no trees, and water but no fish. What am I?|map",
            "What has hands but cannot clap?|clock",
            "I am taken from a mine and shut up in a wooden case, from which I am never released, yet I am used by almost everyone. What am I?|pencil"
        )
        return riddles[difficulty - 1]
    }

    private fun generateTangramData(difficulty: Int): String {
        val shapes = listOf("square", "triangle", "parallelogram", "trapezoid", "house")
        return shapes[difficulty - 1]
    }

    private fun generateUntangleData(difficulty: Int): String {
        val edgeCount = 3 + difficulty * 2
        return edgeCount.toString()
    }

    private fun generateCircuitData(difficulty: Int): String {
        val gridSize = 3 + (difficulty - 1) / 2
        return "$gridSize:${(0 until gridSize * gridSize).map { 0 }.joinToString(",")}"
    }

    private fun generateSolitaireData(difficulty: Int): String {
        val size = 5 + (difficulty - 1) / 2
        return "$size:${(0 until size * size).map { if (it == size * size / 2) "0" else "1" }.joinToString(",")}"
    }

    private fun generateTimingGaugesData(difficulty: Int): String {
        val gauges = 2 + difficulty
        return (1..gauges).map { "${it * 10}" }.joinToString(",")
    }

    private fun generatePatternMatchingData(difficulty: Int): String {
        val patternLength = 3 + difficulty
        return (1..patternLength).map { (it % 5).toString() }.shuffled().joinToString(",")
    }

    private fun generateBulbMemoryData(difficulty: Int): String {
        val bulbCount = 4 + difficulty
        return (0 until bulbCount).map { (Math.random() * 100).toInt() }.joinToString(",")
    }

    private fun generateRotatingPictureData(difficulty: Int): String {
        val sections = 4 + (difficulty - 1) / 2
        return (0 until sections).map { (Math.random() * 360).toInt() }.joinToString(",")
    }

    private fun generateLinePartitionData(difficulty: Int): String {
        val lines = 3 + difficulty
        return lines.toString()
    }

    private fun generateLiquidJarsData(difficulty: Int): String {
        val jars = 3 + difficulty
        return (1..jars).map { "${it}:${it * 10}" }.joinToString("|")
    }

    private fun generateDominoData(difficulty: Int): String {
        val dominos = 5 + difficulty * 2
        return (0 until dominos).map { "${Math.random() * 6}:${Math.random() * 6}" }.joinToString("|")
    }

    private fun generateGearTrainData(difficulty: Int): String {
        val gears = 3 + difficulty
        return (1..gears).map { "${it}:${(it * 10) % 360}" }.joinToString("|")
    }

    private fun generateCodeLockData(difficulty: Int): String {
        val digits = 3 + difficulty
        return (1..digits).map { (Math.random() * 10).toInt() }.joinToString("")
    }

    private fun generateOneStrokePathData(difficulty: Int): String {
        val nodes = 5 + difficulty * 2
        return nodes.toString()
    }

    fun getPuzzles(): List<Puzzle> = puzzles
    fun getPuzzle(id: Int): Puzzle? = puzzles.find { it.id == id }
    fun getPuzzlesByFamily(family: PuzzleFamily): List<Puzzle> =
        puzzles.filter { it.family == family }
    fun getCogMilestones(): List<CogMilestone> = cogMilestones
}
