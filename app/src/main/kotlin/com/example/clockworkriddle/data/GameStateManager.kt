package com.example.clockworkriddle.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.clockworkriddle.model.GameState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_state")

class GameStateManager(private val context: Context) {
    private val gameStateKey = stringPreferencesKey("game_state")

    val gameState: Flow<GameState> = context.dataStore.data.map { preferences ->
        val json = preferences[gameStateKey] ?: return@map GameState()
        try {
            Json.decodeFromString<GameState>(json)
        } catch (e: Exception) {
            GameState()
        }
    }

    suspend fun saveGameState(state: GameState) {
        context.dataStore.edit { preferences ->
            preferences[gameStateKey] = Json.encodeToString(state)
        }
    }

    suspend fun updateGameState(update: (GameState) -> GameState) {
        context.dataStore.edit { preferences ->
            val current = preferences[gameStateKey]?.let {
                try {
                    Json.decodeFromString<GameState>(it)
                } catch (e: Exception) {
                    GameState()
                }
            } ?: GameState()

            val updated = update(current)
            preferences[gameStateKey] = Json.encodeToString(updated)
        }
    }

    suspend fun clearGameState() {
        context.dataStore.edit { preferences ->
            preferences.remove(gameStateKey)
        }
    }
}
