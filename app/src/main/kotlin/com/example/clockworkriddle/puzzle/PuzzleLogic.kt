package com.example.clockworkriddle.puzzle

import com.example.clockworkriddle.model.PuzzleFamily
import com.example.clockworkriddle.model.PuzzleMove
import kotlin.math.abs
import kotlin.math.hypot

interface IPuzzleLogic {
    fun isValidMove(move: PuzzleMove): Boolean
    fun applyMove(move: PuzzleMove): Boolean
    fun isSolved(): Boolean
    fun reset()
    fun getHint(): String
    fun getState(): String
    fun loadState(state: String): Boolean
}

class SlidingPuzzleLogic(private val gridSize: Int, initialTiles: List<Int>) : IPuzzleLogic {
    private var tiles = initialTiles.toMutableList()
    private val initialState = initialTiles.toList()
    private var emptyPos = tiles.indexOf(0)

    override fun isValidMove(move: PuzzleMove): Boolean {
        if (move !is PuzzleMove.SlidingMove) return false
        val pos = move.fromRow * gridSize + move.fromCol
        val emptyRow = emptyPos / gridSize
        val emptyCol = emptyPos % gridSize
        val tileRow = pos / gridSize
        val tileCol = pos % gridSize

        return (abs(emptyRow - tileRow) == 1 && emptyCol == tileCol) ||
               (abs(emptyCol - tileCol) == 1 && emptyRow == tileRow)
    }

    override fun applyMove(move: PuzzleMove): Boolean {
        if (!isValidMove(move)) return false
        if (move !is PuzzleMove.SlidingMove) return false

        val pos = move.fromRow * gridSize + move.fromCol
        tiles[emptyPos] = tiles[pos]
        tiles[pos] = 0
        emptyPos = pos
        return true
    }

    override fun isSolved(): Boolean {
        for (i in 0 until tiles.size - 1) {
            if (tiles[i] != i + 1) return false
        }
        return tiles.last() == 0
    }

    override fun reset() {
        tiles = initialState.toMutableList()
        emptyPos = tiles.indexOf(0)
    }

    override fun getHint(): String {
        return "Move tiles adjacent to the empty space to solve the puzzle."
    }

    override fun getState(): String = tiles.joinToString(",")

    override fun loadState(state: String): Boolean {
        return try {
            tiles = state.split(",").map { it.toInt() }.toMutableList()
            emptyPos = tiles.indexOf(0)
            true
        } catch (e: Exception) {
            false
        }
    }
}

class LightsOutLogic(gridSize: Int, initialState: List<List<Boolean>>) : IPuzzleLogic {
    private var grid = initialState.map { it.toMutableList() }.toMutableList()
    private val initialState = initialState.map { it.toList() }
    private val size = gridSize

    override fun isValidMove(move: PuzzleMove): Boolean {
        if (move !is PuzzleMove.LightsOutToggle) return false
        return move.row in 0 until size && move.col in 0 until size
    }

    override fun applyMove(move: PuzzleMove): Boolean {
        if (!isValidMove(move)) return false
        if (move !is PuzzleMove.LightsOutToggle) return false

        grid[move.row][move.col] = !grid[move.row][move.col]
        if (move.row > 0) grid[move.row - 1][move.col] = !grid[move.row - 1][move.col]
        if (move.row < size - 1) grid[move.row + 1][move.col] = !grid[move.row + 1][move.col]
        if (move.col > 0) grid[move.row][move.col - 1] = !grid[move.row][move.col - 1]
        if (move.col < size - 1) grid[move.row][move.col + 1] = !grid[move.row][move.col + 1]

        return true
    }

    override fun isSolved(): Boolean = grid.all { row -> row.all { !it } }

    override fun reset() {
        grid = initialState.map { it.toMutableList() }.toMutableList()
    }

    override fun getHint(): String = "Click lights to toggle them and adjacent lights off."

    override fun getState(): String = grid.joinToString(";") { row ->
        row.joinToString(",") { if (it) "1" else "0" }
    }

    override fun loadState(state: String): Boolean {
        return try {
            grid = state.split(";").map { row ->
                row.split(",").map { it == "1" }.toMutableList()
            }.toMutableList()
            true
        } catch (e: Exception) {
            false
        }
    }
}

class HanoiLogic(numDisks: Int) : IPuzzleLogic {
    private val pegs = Array(3) { ArrayDeque<Int>() }
    private val initialPegs = Array(3) { ArrayDeque<Int>() }

    init {
        for (i in numDisks downTo 1) {
            pegs[0].addLast(i)
            initialPegs[0].addLast(i)
        }
    }

    override fun isValidMove(move: PuzzleMove): Boolean {
        if (move !is PuzzleMove.HanoiMove) return false
        if (move.fromPeg !in 0..2 || move.toPeg !in 0..2) return false
        if (pegs[move.fromPeg].isEmpty()) return false
        if (pegs[move.toPeg].isNotEmpty() &&
            pegs[move.fromPeg].last() > pegs[move.toPeg].last()) return false
        return true
    }

    override fun applyMove(move: PuzzleMove): Boolean {
        if (!isValidMove(move)) return false
        if (move !is PuzzleMove.HanoiMove) return false

        val disk = pegs[move.fromPeg].removeLast()
        pegs[move.toPeg].addLast(disk)
        return true
    }

    override fun isSolved(): Boolean = pegs[2].size == initialPegs[0].size

    override fun reset() {
        for (i in 0..2) {
            pegs[i].clear()
            initialPegs[i].forEach { pegs[i].addLast(it) }
        }
    }

    override fun getHint(): String = "Move all disks to the rightmost peg. Never place a larger disk on a smaller one."

    override fun getState(): String = pegs.joinToString(";") { it.joinToString(",") }

    override fun loadState(state: String): Boolean = true
}

class MagicSquareLogic(size: Int, target: Int) : IPuzzleLogic {
    private val grid = MutableList(size * size) { 0 }
    private val targetSum = target
    private val gridSize = size

    override fun isValidMove(move: PuzzleMove): Boolean = true

    override fun applyMove(move: PuzzleMove): Boolean {
        return true
    }

    override fun isSolved(): Boolean {
        for (row in 0 until gridSize) {
            var sum = 0
            for (col in 0 until gridSize) {
                sum += grid[row * gridSize + col]
            }
            if (sum != targetSum) return false
        }
        return true
    }

    override fun reset() {
        grid.fill(0)
    }

    override fun getHint(): String = "Arrange numbers so each row, column, and diagonal sums to the same value."

    override fun getState(): String = grid.joinToString(",")

    override fun loadState(state: String): Boolean = true
}

class RiddleLogic(val question: String, val acceptedAnswers: List<String>) : IPuzzleLogic {
    private var userAnswer = ""

    override fun isValidMove(move: PuzzleMove): Boolean = true

    override fun applyMove(move: PuzzleMove): Boolean = true

    fun setAnswer(answer: String) {
        userAnswer = answer.lowercase().trim()
    }

    override fun isSolved(): Boolean {
        return acceptedAnswers.any { it.lowercase() == userAnswer }
    }

    override fun reset() {
        userAnswer = ""
    }

    override fun getHint(): String = "Think carefully about the wording of the riddle."

    override fun getState(): String = userAnswer

    override fun loadState(state: String): Boolean {
        userAnswer = state
        return true
    }
}

class TangramLogic : IPuzzleLogic {
    override fun isValidMove(move: PuzzleMove): Boolean = true
    override fun applyMove(move: PuzzleMove): Boolean = true
    override fun isSolved(): Boolean = true
    override fun reset() {}
    override fun getHint(): String = "Rotate and drag pieces to match the target shape."
    override fun getState(): String = ""
    override fun loadState(state: String): Boolean = true
}

class UntangleLogic(edgeCount: Int) : IPuzzleLogic {
    private val vertices = MutableList(6) { Pair(0f, 0f) }
    private val edges = List(edgeCount) { Pair(0, 1) }

    override fun isValidMove(move: PuzzleMove): Boolean = true
    override fun applyMove(move: PuzzleMove): Boolean = true
    override fun isSolved(): Boolean = !hasIntersections()
    override fun reset() {}
    override fun getHint(): String = "Drag vertices to untangle the lines without crossing."
    override fun getState(): String = ""
    override fun loadState(state: String): Boolean = true

    private fun hasIntersections(): Boolean = false
}

class CircuitLogic : IPuzzleLogic {
    override fun isValidMove(move: PuzzleMove): Boolean = true
    override fun applyMove(move: PuzzleMove): Boolean = true
    override fun isSolved(): Boolean = true
    override fun reset() {}
    override fun getHint(): String = "Rotate the circuit pieces to complete the connection."
    override fun getState(): String = ""
    override fun loadState(state: String): Boolean = true
}
