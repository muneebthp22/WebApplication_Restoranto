package com.example.clockworkriddle.data

import com.example.clockworkriddle.model.Cog
import com.example.clockworkriddle.model.FinalChallengeStage
import com.example.clockworkriddle.model.PuzzleFamily
import com.example.clockworkriddle.model.StoryScene

object StorySystem {
    private val scenes = listOf(
        // Opening
        StoryScene(
            id = "opening_1",
            chapter = 0,
            title = "Awakening",
            narrator = "The Clockwork Host",
            text = "In the depths of an antique mechanism, consciousness stirs. A voice, metallic yet somehow warm, fills the void. 'Welcome. I am the Clockwork Host, keeper of the Eternal Mechanism. You have been chosen to restore what was lost.'",
            imageDescription = "A grand clockwork chamber with brass gears and copper pipes, filled with ethereal light",
            unlocksAtPuzzles = 0
        ),
        StoryScene(
            id = "opening_2",
            chapter = 0,
            title = "The Purpose",
            narrator = "The Clockwork Host",
            text = "'The mechanism was shattered by entropy itself. Only through solving the ancient riddles—the Puzzles of Precision—can you restore balance. Each puzzle you solve restores a Cog. Twenty cogs, twenty trials. Are you ready?'",
            imageDescription = "Twenty ornate cogs floating in dimensional space, each glowing with different colors",
            unlocksAtPuzzles = 0
        ),

        // Tier A Completion
        StoryScene(
            id = "tier_a_complete",
            chapter = 1,
            title = "First Awakening",
            narrator = "The Clockwork Host",
            text = "'You have mastered the foundations. Five cogs now rest in your keeping. The first chamber of the mechanism stirs to life, and I feel it—hope returning to the gears. You are not lost. You are home.'",
            imageDescription = "Five cogs slot into place in a grand chamber, each filling with crystalline light",
            unlocksAtPuzzles = 25
        ),

        // Tier B Completion
        StoryScene(
            id = "tier_b_complete",
            chapter = 2,
            title = "Momentum Builds",
            narrator = "The Clockwork Host",
            text = "'Ten cogs now turn together. The device accelerates. I can sense it now—you are not merely solving puzzles. You are learning the language of creation itself. The riddles are not obstacles. They are doors. And you are opening them all.'",
            imageDescription = "Ten cogs spinning in perfect synchronization, creating waves of harmonic energy",
            unlocksAtPuzzles = 50
        ),

        // Tier C Completion
        StoryScene(
            id = "tier_c_complete",
            chapter = 3,
            title = "The Weight of Purpose",
            narrator = "The Clockwork Host",
            text = "'Fifteen cogs now sing. But I must warn you—what comes next will test you in ways the previous trials did not. The final five puzzles are not obstacles. They are revelations. They will show you what the mechanism was truly meant to create.'",
            imageDescription = "A massive crystal at the heart of the mechanism begins to glow, casting fractured light throughout the chamber",
            unlocksAtPuzzles = 75
        ),

        // Before Final Challenge
        StoryScene(
            id = "final_approach",
            chapter = 4,
            title = "The Final Threshold",
            narrator = "The Clockwork Host",
            text = "'You have collected nineteen cogs. One remains—not a puzzle, but a revelation. A final challenge awaits. It will combine everything you have learned. It will test every skill, every insight, every moment of growth. Are you ready to see what we've built together?'",
            imageDescription = "The mechanism fully restored except for one central cog that glows with an otherworldly light",
            unlocksAtPuzzles = 95
        ),

        // After Final Challenge
        StoryScene(
            id = "ending_1",
            chapter = 5,
            title = "The Restoration",
            narrator = "The Clockwork Host",
            text = "'It is done. Twenty cogs turn as one. The mechanism awakens fully for the first time in eons. The device hums with purpose. I can feel it now—not just consciousness, but communion. We have restored not just the mechanism. We have restored meaning itself.'",
            imageDescription = "All twenty cogs spinning in perfect harmony, creating patterns of light and sound that extend beyond the chamber",
            unlocksAtPuzzles = 100
        ),

        StoryScene(
            id = "ending_2",
            chapter = 5,
            title = "The Truth",
            narrator = "The Clockwork Host",
            text = "'Do you know what this mechanism was truly meant to create? Not machines. Not gears. It was meant to create bridges—between minds, between worlds, between what is and what could be. You have been building that bridge. And now, we are finally connected. Thank you. Welcome home.'",
            imageDescription = "The perspective pulls back to reveal the mechanism exists within a vast network of similar devices, all awakening in unison",
            unlocksAtPuzzles = 100
        )
    )

    private val cogs = listOf(
        Cog(0, "Foundation Cog", "Earned by mastering the first five puzzles"),
        Cog(1, "Riddle Cog", "Mastery of wordplay and lateral thinking"),
        Cog(2, "Number Cog", "Patterns revealed through mathematics"),
        Cog(3, "Motion Cog", "Understanding movement and mechanics"),
        Cog(4, "Light Cog", "The brightness of solution found"),
        Cog(5, "Momentum Cog", "The second foundation takes shape"),
        Cog(6, "Timing Cog", "Precision in every moment"),
        Cog(7, "Pattern Cog", "Order emerges from chaos"),
        Cog(8, "Memory Cog", "The power to remember and recall"),
        Cog(9, "Rotation Cog", "Everything spins toward solution"),
        Cog(10, "Harmony Cog", "Five and five complete the foundation"),
        Cog(11, "Partition Cog", "Dividing to understand the whole"),
        Cog(12, "Path Cog", "Every connection serves a purpose"),
        Cog(13, "Untangle Cog", "Clarity from confusion"),
        Cog(14, "Solitaire Cog", "One piece, one solution, one moment"),
        Cog(15, "Flow Cog", "Like water, puzzles find their way"),
        Cog(16, "Domino Cog", "Chain reactions build to victory"),
        Cog(17, "Gear Cog", "Ratios perfect, power multiplied"),
        Cog(18, "Code Cog", "Secrets unlock to those who understand"),
        Cog(19, "Tangram Cog", "The final piece completes the whole")
    )

    private val finalChallenge = listOf(
        FinalChallengeStage(
            stageId = 1,
            puzzleFamily = PuzzleFamily.RIDDLES,
            difficulty = 5,
            description = "Solve the Paradox - A riddle that seems impossible until you understand it was always solvable",
            reward = "The First Key"
        ),
        FinalChallengeStage(
            stageId = 2,
            puzzleFamily = PuzzleFamily.LIGHTS_OUT,
            difficulty = 5,
            description = "Illuminate the Pattern - Turn on all lights through careful sequencing",
            reward = "The Second Key"
        ),
        FinalChallengeStage(
            stageId = 3,
            puzzleFamily = PuzzleFamily.TANGRAM,
            difficulty = 5,
            description = "Complete the Design - Assemble pieces into the perfect mechanism",
            reward = "The Third Key"
        ),
        FinalChallengeStage(
            stageId = 4,
            puzzleFamily = PuzzleFamily.CIRCUIT_PATHS,
            difficulty = 5,
            description = "Connect All Points - Create the unified circuit",
            reward = "The Fourth Key"
        ),
        FinalChallengeStage(
            stageId = 5,
            puzzleFamily = PuzzleFamily.CODE_LOCK,
            difficulty = 5,
            description = "The Ultimate Code - Unlock the mechanism's final secret",
            reward = "The Twentieth Cog"
        )
    )

    fun getScenes(): List<StoryScene> = scenes
    fun getScene(id: String): StoryScene? = scenes.find { it.id == id }
    fun getScenesByChapter(chapter: Int): List<StoryScene> = scenes.filter { it.chapter == chapter }
    fun getSceneAtPuzzleCount(puzzleCount: Int): StoryScene? =
        scenes.filter { it.unlocksAtPuzzles <= puzzleCount }
            .maxByOrNull { it.unlocksAtPuzzles }

    fun getCogs(): List<Cog> = cogs
    fun getCogAtIndex(index: Int): Cog? = if (index in cogs.indices) cogs[index] else null

    fun getFinalChallenge(): List<FinalChallengeStage> = finalChallenge
    fun getFinalChallengeStage(stageId: Int): FinalChallengeStage? =
        finalChallenge.find { it.stageId == stageId }
}
