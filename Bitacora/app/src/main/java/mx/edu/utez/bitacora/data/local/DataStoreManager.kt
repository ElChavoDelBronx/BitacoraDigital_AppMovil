package mx.edu.utez.bitacora.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("preferences")

class DataStoreManager(private val context: Context) {
    companion object {
        val isLoggedInKey = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveLoginState(isLoggedIn: Boolean){
        context.dataStore.edit { preferences ->
            preferences[isLoggedInKey] = isLoggedIn
        }
    }

    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[isLoggedInKey] ?: false
        }
}