package com.example.clockworkriddle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clockworkriddle.data.GameStateManager
import com.example.clockworkriddle.data.PuzzleRepository
import com.example.clockworkriddle.model.GameState
import com.example.clockworkriddle.ui.Screen
import com.example.clockworkriddle.ui.screens.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val gameStateManager = GameStateManager(this)

        setContent {
            PuzzleGameApp(gameStateManager)
        }
    }
}

@Composable
fun PuzzleGameApp(gameStateManager: GameStateManager) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xD4AF37),
            secondary = Color(0xB8860B),
            background = Color(0x0a0a0a),
            surface = Color(0x1a1a1a),
            surfaceVariant = Color(0x2a2a2a)
        )
    ) {
        PuzzleGameContent(gameStateManager)
    }
}

@Composable
fun PuzzleGameContent(gameStateManager: GameStateManager) {
    val navController = rememberNavController()
    val gameState by gameStateManager.gameState.collectAsState(initial = GameState())
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = Screen.Menu.route) {
        composable(Screen.Menu.route) {
            MenuScreen(
                gameState = gameState,
                onNewGame = {
                    scope.launch {
                        gameStateManager.clearGameState()
                        navController.navigate(Screen.PuzzleBook.route)
                    }
                },
                onContinue = {
                    navController.navigate(Screen.PuzzleBook.route)
                },
                onPuzzleBook = {
                    navController.navigate(Screen.PuzzleBook.route)
                },
                onViewCogs = {
                    navController.navigate(Screen.CogCollection.route)
                },
                onStartChallenge = {
                    navController.navigate(Screen.FinalChallenge.route)
                },
                onSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onCredits = {
                    navController.navigate(Screen.Credits.route)
                },
                onDeleteSave = {
                    scope.launch {
                        gameStateManager.clearGameState()
                    }
                }
            )
        }

        composable(Screen.PuzzleBook.route) {
            val puzzles = PuzzleRepository.getPuzzles()
            PuzzleBookScreen(
                puzzles = puzzles,
                completedPuzzles = gameState.completedPuzzles,
                onPuzzleSelect = { puzzleId ->
                    navController.navigate(Screen.PuzzleScreen.createRoute(puzzleId))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            Screen.PuzzleScreen.route,
            arguments = listOf(navArgument("puzzleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getInt("puzzleId") ?: 0
            val puzzle = PuzzleRepository.getPuzzle(puzzleId)

            if (puzzle != null) {
                val isSolved = puzzleId in gameState.completedPuzzles

                PuzzleScreen(
                    puzzle = puzzle,
                    isSolved = isSolved,
                    onHint = {
                        scope.launch {
                            gameStateManager.updateGameState { state ->
                                state.copy()
                            }
                        }
                    },
                    onReset = {
                        scope.launch {
                            gameStateManager.updateGameState { state ->
                                val progress = state.currentProgress.toMutableMap()
                                progress.remove(puzzleId)
                                state.copy(currentProgress = progress)
                            }
                        }
                    },
                    onNext = {
                        scope.launch {
                            gameStateManager.updateGameState { state ->
                                val completed = state.completedPuzzles.toMutableSet()
                                completed.add(puzzleId)

                                val cogsEarned = mutableSetOf<Int>()
                                val completionCount = completed.size
                                for (i in 0..19) {
                                    if (completionCount >= (i + 1) * 5) {
                                        cogsEarned.add(i)
                                    }
                                }

                                state.copy(
                                    completedPuzzles = completed,
                                    unlockedCogs = cogsEarned
                                )
                            }
                            navController.popBackStack()
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                gameState = gameState,
                onMusicToggle = { enabled ->
                    scope.launch {
                        gameStateManager.updateGameState { state ->
                            state.copy(musicEnabled = enabled)
                        }
                    }
                },
                onSoundToggle = { enabled ->
                    scope.launch {
                        gameStateManager.updateGameState { state ->
                            state.copy(soundEnabled = enabled)
                        }
                    }
                },
                onHapticsToggle = { enabled ->
                    scope.launch {
                        gameStateManager.updateGameState { state ->
                            state.copy(hapticsEnabled = enabled)
                        }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Credits.route) {
            CreditsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.CogCollection.route) {
            CogCollectionScreen(
                collectedCogs = gameState.unlockedCogs,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.FinalChallenge.route) {
            FinalChallengeScreen(
                currentStage = 1,
                completedStages = emptySet(),
                onStageStart = { stageId ->
                    // Navigate to puzzle for this stage
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            Screen.StoryScene.route,
            arguments = listOf(navArgument("sceneId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sceneId = backStackEntry.arguments?.getString("sceneId") ?: "opening_1"
            StoryScreen(
                sceneId = sceneId,
                onContinue = {
                    // Handle story progression
                },
                onClose = {
                    navController.popBackStack()
                }
            )
        }
    }
}
