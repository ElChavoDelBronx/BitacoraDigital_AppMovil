package mx.edu.utez.bitacora.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("preferences")

class DataStoreManager(private val context: Context) {
    companion object {
        val tokenKey = stringPreferencesKey("auth_token")
        val userId = longPreferencesKey("user_id")
        val userName = stringPreferencesKey("user_name")
    }

    suspend fun saveToken(token: String){
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }
    suspend fun saveUserId(id: Long) {
        context.dataStore.edit { preferences ->
            preferences[userId] = id
        }
    }
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[userName] = name
        }
    }


    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val token = preferences[tokenKey]
            !token.isNullOrBlank()
        }

    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[tokenKey] }
    val userIdFlow: Flow<Long?> = context.dataStore.data
        .map { preferences -> preferences[userId] }
    val userNameFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[userName] }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}