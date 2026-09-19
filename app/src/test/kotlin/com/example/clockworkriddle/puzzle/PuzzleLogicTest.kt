package com.example.clockworkriddle.puzzle

import com.example.clockworkriddle.model.PuzzleMove
import org.junit.Test
import org.junit.Assert.*

class SlidingPuzzleLogicTest {
    @Test
    fun testInitialState() {
        val tiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val puzzle = SlidingPuzzleLogic(3, tiles)
        assertFalse(puzzle.isSolved())
    }

    @Test
    fun testValidMove() {
        val tiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val puzzle = SlidingPuzzleLogic(3, tiles)
        val move = PuzzleMove.SlidingMove(1, 2)
        assertTrue(puzzle.isValidMove(move))
    }

    @Test
    fun testApplyMove() {
        val tiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val puzzle = SlidingPuzzleLogic(3, tiles)
        val move = PuzzleMove.SlidingMove(2, 2)
        assertTrue(puzzle.applyMove(move))
    }

    @Test
    fun testInvalidMove() {
        val tiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val puzzle = SlidingPuzzleLogic(3, tiles)
        val move = PuzzleMove.SlidingMove(0, 0)
        assertFalse(puzzle.isValidMove(move))
    }

    @Test
    fun testSolvedState() {
        val tiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val puzzle = SlidingPuzzleLogic(3, tiles)
        assertTrue(puzzle.isSolved())
    }

    @Test
    fun testReset() {
        val tiles = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
        val puzzle = SlidingPuzzleLogic(3, tiles)
        puzzle.applyMove(PuzzleMove.SlidingMove(2, 2))
        puzzle.reset()
        assertTrue(puzzle.isSolved())
    }
}

class LightsOutLogicTest {
    @Test
    fun testInitialState() {
        val grid = listOf(
            listOf(true, false, true),
            listOf(false, true, false),
            listOf(true, false, true)
        )
        val puzzle = LightsOutLogic(3, grid)
        assertFalse(puzzle.isSolved())
    }

    @Test
    fun testValidMove() {
        val grid = listOf(
            listOf(true, false, true),
            listOf(false, true, false),
            listOf(true, false, true)
        )
        val puzzle = LightsOutLogic(3, grid)
        val move = PuzzleMove.LightsOutToggle(1, 1)
        assertTrue(puzzle.isValidMove(move))
    }

    @Test
    fun testApplyMove() {
        val grid = listOf(
            listOf(true, false, true),
            listOf(false, true, false),
            listOf(true, false, true)
        )
        val puzzle = LightsOutLogic(3, grid)
        val move = PuzzleMove.LightsOutToggle(1, 1)
        assertTrue(puzzle.applyMove(move))
    }

    @Test
    fun testSolvedState() {
        val grid = listOf(
            listOf(false, false, false),
            listOf(false, false, false),
            listOf(false, false, false)
        )
        val puzzle = LightsOutLogic(3, grid)
        assertTrue(puzzle.isSolved())
    }
}

class HanoiLogicTest {
    @Test
    fun testInitialState() {
        val puzzle = HanoiLogic(3)
        assertFalse(puzzle.isSolved())
    }

    @Test
    fun testValidMove() {
        val puzzle = HanoiLogic(3)
        val move = PuzzleMove.HanoiMove(0, 1)
        assertTrue(puzzle.isValidMove(move))
    }

    @Test
    fun testApplyMove() {
        val puzzle = HanoiLogic(3)
        val move = PuzzleMove.HanoiMove(0, 1)
        assertTrue(puzzle.applyMove(move))
    }

    @Test
    fun testInvalidMoveFromEmptyPeg() {
        val puzzle = HanoiLogic(3)
        val move = PuzzleMove.HanoiMove(1, 0)
        assertFalse(puzzle.isValidMove(move))
    }
}

class RiddleLogicTest {
    @Test
    fun testUnsolved() {
        val puzzle = RiddleLogic("What am I?", listOf("answer", "a"))
        assertFalse(puzzle.isSolved())
    }

    @Test
    fun testSolvedWithCorrectAnswer() {
        val puzzle = RiddleLogic("What am I?", listOf("answer", "a"))
        puzzle.setAnswer("answer")
        assertTrue(puzzle.isSolved())
    }

    @Test
    fun testSolvedWithAlternateAnswer() {
        val puzzle = RiddleLogic("What am I?", listOf("answer", "a"))
        puzzle.setAnswer("a")
        assertTrue(puzzle.isSolved())
    }

    @Test
    fun testNotSolvedWithWrongAnswer() {
        val puzzle = RiddleLogic("What am I?", listOf("answer", "a"))
        puzzle.setAnswer("wrong")
        assertFalse(puzzle.isSolved())
    }

    @Test
    fun testCaseInsensitive() {
        val puzzle = RiddleLogic("What am I?", listOf("answer"))
        puzzle.setAnswer("ANSWER")
        assertTrue(puzzle.isSolved())
    }

    @Test
    fun testTrimsWhitespace() {
        val puzzle = RiddleLogic("What am I?", listOf("answer"))
        puzzle.setAnswer("  answer  ")
        assertTrue(puzzle.isSolved())
    }
}
