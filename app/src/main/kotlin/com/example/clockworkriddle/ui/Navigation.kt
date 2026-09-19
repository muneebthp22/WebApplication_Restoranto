package com.example.clockworkriddle.ui

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object PuzzleBook : Screen("puzzle_book")
    object PuzzleScreen : Screen("puzzle/{puzzleId}") {
        fun createRoute(puzzleId: Int) = "puzzle/$puzzleId"
    }
    object Settings : Screen("settings")
    object Credits : Screen("credits")
    object Story : Screen("story/{sceneId}") {
        fun createRoute(sceneId: Int) = "story/$sceneId"
    }
}
