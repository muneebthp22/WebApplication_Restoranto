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

class TangramLogic(
    private val targetShape: List<Pair<Float, Float>>,
    private val levelDifficulty: Int
) : IPuzzleLogic {
    data class TangramPiece(
        val id: Int,
        val type: PieceType,
        var x: Float = 0f,
        var y: Float = 0f,
        var rotation: Int = 0
    )

    enum class PieceType {
        LARGE_TRIANGLE, MEDIUM_TRIANGLE, SMALL_TRIANGLE,
        SQUARE, PARALLELOGRAM
    }

    private val pieces = listOf(
        TangramPiece(0, PieceType.LARGE_TRIANGLE, 50f, 50f, 0),
        TangramPiece(1, PieceType.LARGE_TRIANGLE, 150f, 50f, 0),
        TangramPiece(2, PieceType.MEDIUM_TRIANGLE, 100f, 200f, 0),
        TangramPiece(3, PieceType.SMALL_TRIANGLE, 50f, 300f, 0),
        TangramPiece(4, PieceType.SMALL_TRIANGLE, 150f, 300f, 0),
        TangramPiece(5, PieceType.SQUARE, 100f, 350f, 0),
        TangramPiece(6, PieceType.PARALLELOGRAM, 200f, 350f, 0)
    )
    private val initialState = pieces.map { it.copy() }

    override fun isValidMove(move: PuzzleMove): Boolean {
        return move is PuzzleMove.TangramMove &&
               move.pieceId in 0..6
    }

    override fun applyMove(move: PuzzleMove): Boolean {
        if (!isValidMove(move)) return false
        if (move !is PuzzleMove.TangramMove) return false

        val piece = pieces.find { it.id == move.pieceId } ?: return false
        piece.x = move.x
        piece.y = move.y
        piece.rotation = move.rotation % 360
        return true
    }

    override fun isSolved(): Boolean {
        if (levelDifficulty == 0) return true
        return pieces.all { piece ->
            targetShape.any { target ->
                kotlin.math.hypot(piece.x - target.first, piece.y - target.second) < 50f
            }
        }
    }

    override fun reset() {
        for (i in pieces.indices) {
            pieces[i].x = initialState[i].x
            pieces[i].y = initialState[i].y
            pieces[i].rotation = initialState[i].rotation
        }
    }

    override fun getHint(): String = when (levelDifficulty) {
        1 -> "Start with the two large triangles as the base."
        2 -> "Place corner pieces first, then fill in the middle."
        3 -> "Use the square as an anchor point."
        4 -> "The parallelogram can rotate 45 degrees for better fit."
        5 -> "All 7 pieces must connect to form the complete shape."
        else -> "Rotate and drag pieces to match the target shape."
    }

    override fun getState(): String = pieces.joinToString("|") { piece ->
        "${piece.id},${piece.x},${piece.y},${piece.rotation}"
    }

    override fun loadState(state: String): Boolean {
        return try {
            state.split("|").forEach { pieceStr ->
                val parts = pieceStr.split(",")
                val id = parts[0].toInt()
                val piece = pieces.find { it.id == id } ?: return false
                piece.x = parts[1].toFloat()
                piece.y = parts[2].toFloat()
                piece.rotation = parts[3].toInt()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

class UntangleLogic(private val edgeCount: Int, initialVertices: List<Pair<Float, Float>>) : IPuzzleLogic {
    private var vertices = initialVertices.toMutableList()
    private val edges = mutableListOf<Pair<Int, Int>>()
    private val initialVertices = initialVertices.toList()

    init {
        for (i in 0 until edgeCount) {
            edges.add(Pair(i % vertices.size, (i + 1) % vertices.size))
        }
    }

    override fun isValidMove(move: PuzzleMove): Boolean {
        return move is PuzzleMove.UntangleMove &&
               move.vertexId in vertices.indices
    }

    override fun applyMove(move: PuzzleMove): Boolean {
        if (!isValidMove(move)) return false
        if (move !is PuzzleMove.UntangleMove) return false

        vertices[move.vertexId] = Pair(move.x, move.y)
        return true
    }

    override fun isSolved(): Boolean = !hasIntersections()

    override fun reset() {
        vertices = initialVertices.toMutableList()
    }

    override fun getHint(): String = "Drag vertices to untangle the lines without crossing."

    override fun getState(): String = vertices.joinToString("|") { "${it.first},${it.second}" }

    override fun loadState(state: String): Boolean {
        return try {
            vertices = state.split("|").map {
                val parts = it.split(",")
                Pair(parts[0].toFloat(), parts[1].toFloat())
            }.toMutableList()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun hasIntersections(): Boolean {
        for (i in edges.indices) {
            for (j in i + 1 until edges.size) {
                if (doSegmentsIntersect(
                    vertices[edges[i].first], vertices[edges[i].second],
                    vertices[edges[j].first], vertices[edges[j].second]
                )) {
                    return true
                }
            }
        }
        return false
    }

    private fun doSegmentsIntersect(p1: Pair<Float, Float>, p2: Pair<Float, Float>,
                                     p3: Pair<Float, Float>, p4: Pair<Float, Float>): Boolean {
        val ccw = { a: Pair<Float, Float>, b: Pair<Float, Float>, c: Pair<Float, Float> ->
            (c.second - a.second) * (b.first - a.first) > (b.second - a.second) * (c.first - a.first)
        }
        return ccw(p1, p3, p4) != ccw(p2, p3, p4) && ccw(p1, p2, p3) != ccw(p1, p2, p4)
    }
}

class CircuitLogic(private val gridSize: Int, initialRotations: List<Int>) : IPuzzleLogic {
    private var rotations = initialRotations.toMutableList()
    private val initialRotations = initialRotations.toList()
    private val targetConnections = mutableSetOf<Pair<Int, Int>>()

    init {
        val size = gridSize * gridSize
        for (i in 0 until size) {
            val row = i / gridSize
            val col = i % gridSize
            if (col < gridSize - 1) targetConnections.add(Pair(i, i + 1))
            if (row < gridSize - 1) targetConnections.add(Pair(i, i + gridSize))
        }
    }

    override fun isValidMove(move: PuzzleMove): Boolean {
        return move is PuzzleMove.RotationMove &&
               move.itemId in rotations.indices
    }

    override fun applyMove(move: PuzzleMove): Boolean {
        if (!isValidMove(move)) return false
        if (move !is PuzzleMove.RotationMove) return false

        rotations[move.itemId] = (rotations[move.itemId] + move.rotation) % 360
        return true
    }

    override fun isSolved(): Boolean {
        return isFullyConnected()
    }

    override fun reset() {
        rotations = initialRotations.toMutableList()
    }

    override fun getHint(): String = "Rotate circuit pieces until all connections align."

    override fun getState(): String = rotations.joinToString(",")

    override fun loadState(state: String): Boolean {
        return try {
            rotations = state.split(",").map { it.toInt() }.toMutableList()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isFullyConnected(): Boolean {
        val size = gridSize * gridSize
        val visited = BooleanArray(size)
        val queue = ArrayDeque<Int>()
        queue.add(0)
        visited[0] = true

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val row = current / gridSize
            val col = current % gridSize

            listOf(
                Pair(row - 1, col),
                Pair(row + 1, col),
                Pair(row, col - 1),
                Pair(row, col + 1)
            ).forEach { (r, c) ->
                if (r in 0 until gridSize && c in 0 until gridSize) {
                    val neighbor = r * gridSize + c
                    if (!visited[neighbor] && isConnected(current, neighbor)) {
                        visited[neighbor] = true
                        queue.add(neighbor)
                    }
                }
            }
        }

        return visited.all { it }
    }

    private fun isConnected(from: Int, to: Int): Boolean {
        val rotation = rotations[from] / 90
        return when {
            to == from + 1 && rotation % 2 == 0 -> true
            to == from - 1 && rotation % 2 == 0 -> true
            to == from + gridSize && rotation % 2 == 1 -> true
            to == from - gridSize && rotation % 2 == 1 -> true
            else -> false
        }
    }
}
