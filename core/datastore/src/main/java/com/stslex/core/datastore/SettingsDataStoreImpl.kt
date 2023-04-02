package com.stslex.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStoreImpl(
    private val context: Context
) : SettingsDataStore {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val IS_SYSTEM_ENABLE = booleanPreferencesKey("IS_SYSTEM_ENABLE")
        private val IS_DARK_THEME = booleanPreferencesKey("IS_DARK_THEME")
    }

    override val settings: Flow<SettingsDbModel>
        get() = context.dataStore.data.map { prefs ->
            SettingsDbModel(
                isSystemThemeEnable = prefs[IS_SYSTEM_ENABLE] ?: true,
                isDarkTheme = prefs[IS_DARK_THEME] ?: false
            )
        }

    override suspend fun updateSettings(settings: SettingsDbModel) {
        context.dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                set(IS_SYSTEM_ENABLE, settings.isSystemThemeEnable)
                set(IS_DARK_THEME, settings.isDarkTheme)
            }
        }
    }
}