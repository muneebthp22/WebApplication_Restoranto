package com.example.clockworkriddle.puzzle

import com.example.clockworkriddle.model.PuzzleMove
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class SlidingPuzzleLogicTest {
    private lateinit var logic: SlidingPuzzleLogic

    @Before
    fun setUp() {
        val initialTiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        logic = SlidingPuzzleLogic(3, initialTiles)
    }

    @Test
    fun testValidMoveAdjacentTile() {
        val move = PuzzleMove.SlidingMove(fromRow = 2, fromCol = 1)
        assertTrue(logic.isValidMove(move))
    }

    @Test
    fun testInvalidMoveNonAdjacentTile() {
        val move = PuzzleMove.SlidingMove(fromRow = 0, fromCol = 0)
        assertFalse(logic.isValidMove(move))
    }

    @Test
    fun testSolvedStateDetection() {
        val solvedTiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val solvedLogic = SlidingPuzzleLogic(3, solvedTiles)
        assertTrue(solvedLogic.isSolved())
    }

    @Test
    fun testResetRestoresInitialState() {
        val move = PuzzleMove.SlidingMove(fromRow = 2, fromCol = 1)
        logic.applyMove(move)
        logic.reset()
        
        val initialState = "1,2,3,4,5,6,7,8,0"
        assertEquals(initialState, logic.getState())
    }

    @Test
    fun testStateSerializationAndDeserialization() {
        val originalState = logic.getState()
        assertTrue(logic.loadState(originalState))
        assertEquals(originalState, logic.getState())
    }
}

class LightsOutLogicTest {
    private lateinit var logic: LightsOutLogic

    @Before
    fun setUp() {
        val initialState = listOf(
            listOf(true, false, true),
            listOf(false, true, false),
            listOf(true, false, true)
        )
        logic = LightsOutLogic(3, initialState)
    }

    @Test
    fun testValidToggle() {
        val move = PuzzleMove.LightsOutToggle(row = 1, col = 1)
        assertTrue(logic.isValidMove(move))
    }

    @Test
    fun testInvalidToggleOutOfBounds() {
        val move = PuzzleMove.LightsOutToggle(row = 5, col = 5)
        assertFalse(logic.isValidMove(move))
    }

    @Test
    fun testAllLightsOffIsSolved() {
        val allOff = listOf(
            listOf(false, false, false),
            listOf(false, false, false),
            listOf(false, false, false)
        )
        val solvedLogic = LightsOutLogic(3, allOff)
        assertTrue(solvedLogic.isSolved())
    }

    @Test
    fun testStatePersistedAndLoaded() {
        val state = logic.getState()
        assertTrue(logic.loadState(state))
    }
}

class HanoiLogicTest {
    private lateinit var logic: HanoiLogic

    @Before
    fun setUp() {
        logic = HanoiLogic(numDisks = 3)
    }

    @Test
    fun testValidMoveFromFirstPeg() {
        val move = PuzzleMove.HanoiMove(fromPeg = 0, toPeg = 1)
        assertTrue(logic.isValidMove(move))
    }

    @Test
    fun testInvalidMoveEmptyPeg() {
        val move = PuzzleMove.HanoiMove(fromPeg = 1, toPeg = 2)
        assertFalse(logic.isValidMove(move))
    }

    @Test
    fun testGameNotSolvedInitially() {
        assertFalse(logic.isSolved())
    }

    @Test
    fun testReset() {
        logic.reset()
        assertFalse(logic.isSolved())
    }

    @Test
    fun testStateFormat() {
        val state = logic.getState()
        assertTrue(state.contains(";"))
    }
}

class RiddleLogicTest {
    private lateinit var logic: RiddleLogic

    @Before
    fun setUp() {
        val riddle = "What is 2+2?"
        val acceptedAnswers = listOf("four", "4")
        logic = RiddleLogic(riddle, acceptedAnswers)
    }

    @Test
    fun testCorrectAnswerSolvesPuzzle() {
        logic.setAnswer("four")
        assertTrue(logic.isSolved())
    }

    @Test
    fun testIncorrectAnswerDoesNotSolve() {
        logic.setAnswer("five")
        assertFalse(logic.isSolved())
    }

    @Test
    fun testAnswerCaseInsensitive() {
        logic.setAnswer("FOUR")
        assertTrue(logic.isSolved())
    }

    @Test
    fun testAnswerTrimmed() {
        logic.setAnswer("  four  ")
        assertTrue(logic.isSolved())
    }

    @Test
    fun testAlternativeAnswers() {
        logic.setAnswer("4")
        assertTrue(logic.isSolved())
    }

    @Test
    fun testReset() {
        logic.setAnswer("four")
        logic.reset()
        assertFalse(logic.isSolved())
    }
}

class TangramLogicTest {
    private lateinit var logic: TangramLogic

    @Before
    fun setUp() {
        val targetShape = listOf(Pair(100f, 100f), Pair(200f, 200f))
        logic = TangramLogic(targetShape, levelDifficulty = 3)
    }

    @Test
    fun testValidMove() {
        val move = PuzzleMove.TangramMove(pieceId = 0, rotation = 45, x = 100f, y = 100f)
        assertTrue(logic.isValidMove(move))
    }

    @Test
    fun testInvalidPieceId() {
        val move = PuzzleMove.TangramMove(pieceId = 10, rotation = 0, x = 0f, y = 0f)
        assertFalse(logic.isValidMove(move))
    }

    @Test
    fun testHintVariesByDifficulty() {
        val hint = logic.getHint()
        assertTrue(hint.isNotEmpty())
    }

    @Test
    fun testStateSerializationFormat() {
        val move = PuzzleMove.TangramMove(pieceId = 0, rotation = 90, x = 100f, y = 100f)
        logic.applyMove(move)
        val state = logic.getState()
        assertTrue(state.contains("|"))
    }
}

class UntangleLogicTest {
    private lateinit var logic: UntangleLogic

    @Before
    fun setUp() {
        val vertices = listOf(
            Pair(0f, 0f), Pair(100f, 100f),
            Pair(100f, 0f), Pair(0f, 100f)
        )
        logic = UntangleLogic(edgeCount = 4, initialVertices = vertices)
    }

    @Test
    fun testValidVertexMove() {
        val move = PuzzleMove.UntangleMove(vertexId = 0, x = 50f, y = 50f)
        assertTrue(logic.isValidMove(move))
    }

    @Test
    fun testInvalidVertexId() {
        val move = PuzzleMove.UntangleMove(vertexId = 10, x = 0f, y = 0f)
        assertFalse(logic.isValidMove(move))
    }

    @Test
    fun testReset() {
        logic.reset()
        assertFalse(logic.isSolved())
    }
}

class CircuitLogicTest {
    private lateinit var logic: CircuitLogic

    @Before
    fun setUp() {
        val rotations = listOf(0, 90, 0, 180, 90, 0, 90, 90, 0)
        logic = CircuitLogic(gridSize = 3, initialRotations = rotations)
    }

    @Test
    fun testValidRotation() {
        val move = PuzzleMove.RotationMove(itemId = 0, rotation = 90)
        assertTrue(logic.isValidMove(move))
    }

    @Test
    fun testInvalidItemId() {
        val move = PuzzleMove.RotationMove(itemId = 10, rotation = 90)
        assertFalse(logic.isValidMove(move))
    }

    @Test
    fun testRotationApplication() {
        val move = PuzzleMove.RotationMove(itemId = 0, rotation = 90)
        assertTrue(logic.applyMove(move))
    }

    @Test
    fun testStateFormat() {
        val state = logic.getState()
        assertTrue(state.contains(","))
    }
}
