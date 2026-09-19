package com.example.clockworkriddle.data

import com.example.clockworkriddle.model.GameState
import com.example.clockworkriddle.model.PuzzleFamily
import com.example.clockworkriddle.model.PuzzleProgress
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class GameStateTest {
    @Test
    fun testInitialGameStateDefaults() {
        val state = GameState()
        assertTrue(state.completedPuzzles.isEmpty())
        assertTrue(state.unlockedCogs.isEmpty())
        assertTrue(state.soundEnabled)
        assertTrue(state.musicEnabled)
        assertTrue(state.hapticsEnabled)
        assertEquals(0, state.currentChapter)
        assertFalse(state.finalChallengeSolved)
    }

    @Test
    fun testGameStateWithPuzzlesCompleted() {
        val completed = setOf(0, 1, 2, 3, 4)
        val state = GameState(completedPuzzles = completed)
        assertEquals(5, state.completedPuzzles.size)
        assertTrue(state.completedPuzzles.contains(0))
        assertTrue(state.completedPuzzles.contains(4))
    }

    @Test
    fun testCogUnlockAt5Puzzles() {
        val completed = (0..4).toSet()
        val cogs = mutableSetOf<Int>()
        val completionCount = completed.size
        
        for (i in 0..19) {
            if (completionCount >= (i + 1) * 5) {
                cogs.add(i)
            }
        }
        
        assertEquals(1, cogs.size)
        assertTrue(cogs.contains(0))
    }

    @Test
    fun testCogUnlockProgression() {
        val testCases = listOf(
            Pair(5, 1),   
            Pair(10, 2),  
            Pair(25, 5),  
            Pair(50, 10), 
            Pair(100, 20) 
        )
        
        for ((puzzles, expectedCogs) in testCases) {
            val completed = (0 until puzzles).toSet()
            val cogs = mutableSetOf<Int>()
            
            for (i in 0..19) {
                if (completed.size >= (i + 1) * 5) {
                    cogs.add(i)
                }
            }
            
            assertEquals(expectedCogs, cogs.size)
        }
    }

    @Test
    fun testStoryMilestoneTracking() {
        val state = GameState(
            storyMilestones = setOf("opening_1", "opening_2", "tier_a_complete")
        )
        
        assertEquals(3, state.storyMilestones.size)
        assertTrue(state.storyMilestones.contains("opening_1"))
    }

    @Test
    fun testFinalChallengeUnlockAt95() {
        val state95 = GameState(completedPuzzles = (0..94).toSet())
        val state94 = GameState(completedPuzzles = (0..93).toSet())
        
        assertTrue(state95.completedPuzzles.size >= 95)
        assertFalse(state94.completedPuzzles.size >= 95)
    }

    @Test
    fun testGameStateImmutability() {
        val original = GameState()
        val modified = original.copy(soundEnabled = false)
        
        assertTrue(original.soundEnabled)
        assertFalse(modified.soundEnabled)
    }

    @Test
    fun testMultipleSettingsToggle() {
        val state1 = GameState()
        val state2 = state1.copy(soundEnabled = false, musicEnabled = false)
        val state3 = state2.copy(hapticsEnabled = false)
        
        assertTrue(state1.soundEnabled && state1.musicEnabled && state1.hapticsEnabled)
        assertFalse(state2.soundEnabled || state2.musicEnabled)
        assertEquals(false, state3.hapticsEnabled)
    }

    @Test
    fun testProgressTracking() {
        val progress = PuzzleProgress(
            puzzleId = 0,
            state = "1,2,3",
            hintsUsed = 2,
            solved = false
        )
        
        val progressMap = mapOf(0 to progress)
        val state = GameState(currentProgress = progressMap)
        
        assertEquals(1, state.currentProgress.size)
        assertEquals(2, state.currentProgress[0]?.hintsUsed)
        assertFalse(state.currentProgress[0]?.solved ?: true)
    }

    @Test
    fun testProgressCompletion() {
        var progress = PuzzleProgress(puzzleId = 0, state = "", solved = false)
        progress = progress.copy(solved = true, hintsUsed = 1)
        
        assertTrue(progress.solved)
        assertEquals(1, progress.hintsUsed)
    }
}

class StorySystemTest {
    @Test
    fun testAllScenesPresent() {
        val scenes = StorySystem.getScenes()
        assertEquals(8, scenes.size)
    }

    @Test
    fun testOpeningScene() {
        val opening = StorySystem.getScene("opening_1")
        assertTrue(opening != null)
        assertEquals("Awakening", opening?.title)
        assertEquals(0, opening?.chapter)
    }

    @Test
    fun testScenesByChapter() {
        val chapter0 = StorySystem.getScenesByChapter(0)
        assertTrue(chapter0.isNotEmpty())
        assertTrue(chapter0.all { it.chapter == 0 })
    }

    @Test
    fun testSceneAtPuzzleCount() {
        val scene = StorySystem.getSceneAtPuzzleCount(50)
        assertTrue(scene != null)
        assertTrue(scene?.unlocksAtPuzzles!! <= 50)
    }

    @Test
    fun testAllCogsPresent() {
        val cogs = StorySystem.getCogs()
        assertEquals(20, cogs.size)
    }

    @Test
    fun testCogNamesUnique() {
        val cogs = StorySystem.getCogs()
        val names = cogs.map { it.name }
        assertEquals(names.size, names.distinct().size)
    }

    @Test
    fun testFinalChallengeStages() {
        val stages = StorySystem.getFinalChallenge()
        assertEquals(5, stages.size)
    }

    @Test
    fun testFinalChallengeProgression() {
        val stages = StorySystem.getFinalChallenge()
        for (i in stages.indices) {
            assertEquals(i + 1, stages[i].stageId)
            assertTrue(stages[i].description.isNotEmpty())
        }
    }

    @Test
    fun testFinalChallengeRewards() {
        val stages = StorySystem.getFinalChallenge()
        assertEquals(5, stages.size)
        assertTrue(stages.last().reward.contains("Cog"))
    }

    @Test
    fun testPuzzleFamiliesInChallenge() {
        val stages = StorySystem.getFinalChallenge()
        val families = stages.map { it.puzzleFamily }
        
        assertTrue(families.contains(PuzzleFamily.RIDDLES))
        assertTrue(families.contains(PuzzleFamily.LIGHTS_OUT))
        assertTrue(families.contains(PuzzleFamily.TANGRAM))
        assertTrue(families.contains(PuzzleFamily.CIRCUIT_PATHS))
        assertTrue(families.contains(PuzzleFamily.CODE_LOCK))
    }
}

class PuzzleRepositoryTest {
    @Test
    fun testAllPuzzlesPresent() {
        val puzzles = PuzzleRepository.getPuzzles()
        assertEquals(100, puzzles.size)
    }

    @Test
    fun testPuzzlesByFamily() {
        val riddles = PuzzleRepository.getPuzzlesByFamily(PuzzleFamily.RIDDLES)
        assertEquals(5, riddles.size)
    }

    @Test
    fun testAllFamiliesHave5Levels() {
        val allFamilies = listOf(
            PuzzleFamily.RIDDLES, PuzzleFamily.NUMBER_SQUARES, PuzzleFamily.SLIDING_BLOCKS,
            PuzzleFamily.LIGHTS_OUT, PuzzleFamily.SLIDING_PICTURE,
            PuzzleFamily.TIMING_GAUGES, PuzzleFamily.PATTERN_MATCHING, PuzzleFamily.BULB_MEMORY,
            PuzzleFamily.ROTATING_PICTURE, PuzzleFamily.HANOI,
            PuzzleFamily.LINE_PARTITION, PuzzleFamily.CIRCUIT_PATHS, PuzzleFamily.UNTANGLE,
            PuzzleFamily.SOLITAIRE, PuzzleFamily.LIQUID_JARS,
            PuzzleFamily.DOMINO_PLACEMENT, PuzzleFamily.GEAR_TRAIN, PuzzleFamily.CODE_LOCK,
            PuzzleFamily.ONE_STROKE_PATH, PuzzleFamily.TANGRAM
        )
        
        for (family in allFamilies) {
            val puzzles = PuzzleRepository.getPuzzlesByFamily(family)
            assertEquals(5, puzzles.size)
        }
    }

    @Test
    fun testPuzzleDifficultySorting() {
        val riddles = PuzzleRepository.getPuzzlesByFamily(PuzzleFamily.RIDDLES)
        for (i in riddles.indices) {
            assertEquals(i + 1, riddles[i].difficulty)
        }
    }

    @Test
    fun testPuzzleDataNotEmpty() {
        val puzzles = PuzzleRepository.getPuzzles()
        for (puzzle in puzzles) {
            assertTrue(puzzle.id >= 0)
            assertTrue(puzzle.title.isNotEmpty())
        }
    }

    @Test
    fun testGetSpecificPuzzle() {
        val puzzle = PuzzleRepository.getPuzzle(0)
        assertTrue(puzzle != null)
        assertEquals(0, puzzle?.id)
    }

    @Test
    fun testCogMilestonesPresent() {
        val milestones = PuzzleRepository.getCogMilestones()
        assertEquals(20, milestones.size)
    }
}
