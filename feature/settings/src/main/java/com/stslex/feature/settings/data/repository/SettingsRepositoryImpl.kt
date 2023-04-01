package com.stslex.feature.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        private val IS_SYSTEM_ENABLE = booleanPreferencesKey("IS_SYSTEM_ENABLE")
        private val IS_DARK_THEME = booleanPreferencesKey("IS_DARK_THEME")
    }

    override val settings: Flow<SettingsDbModel>
        get() = dataStore.data.map { prefs ->
            SettingsDbModel(
                isSystemThemeEnable = prefs[IS_SYSTEM_ENABLE] ?: true,
                isDarkTheme = prefs[IS_DARK_THEME] ?: false
            )
        }

    override suspend fun updateSettings(settingsDbModel: SettingsDbModel) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                set(IS_SYSTEM_ENABLE, settingsDbModel.isSystemThemeEnable)
                set(IS_DARK_THEME, settingsDbModel.isDarkTheme)
            }
        }
    }
}
