package com.akvelon.wallpaper.controller

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.akvelon.wallpaper.controller.AppState.Companion.APP_STATE
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime
import com.akvelon.wallpaper.controller.model.GeneratePreferences
import com.akvelon.wallpaper.controller.model.SettingsPreferences
import com.akvelon.wallpaper.extension.ordinalToEnum
import com.akvelon.wallpaper.extension.withIO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_STATE
)

@Singleton
class AppState @Inject constructor(
    app: Application
) {

    companion object {
        const val APP_STATE = "app_state"
        private const val LANGUAGE = "language"
        private const val WORK_ID = "work_id"
        private const val UPDATE_TIME = "update_time"
        private const val LAST_IMAGE_URI = "last_image_uri"
        val LANGUAGE_KEY = intPreferencesKey(LANGUAGE)
        val WORK_ID_KEY = stringPreferencesKey(WORK_ID)
        val UPDATE_TIME_KEY = intPreferencesKey(UPDATE_TIME)
        val LAST_IMAGE_URI_KEY = stringPreferencesKey(LAST_IMAGE_URI)
    }

    private val dataStore = app.dataStore
    private val preferences = dataStore.data
    val generatePreferences = preferences.map {
        GeneratePreferences(
            lastImageUri = it[LAST_IMAGE_URI_KEY]?.let { uri -> Uri.parse(uri) }
        )
    }
    val settingsPreferences = preferences.map {
        SettingsPreferences(
            language = ordinalToEnum(Language.values(), it[LANGUAGE_KEY]) ?: Language.ENGLISH,
            updateTime = ordinalToEnum(
                UpdateTime.values(),
                it[UPDATE_TIME_KEY]
            ) ?: UpdateTime.ONE_DAY
        )
    }

    suspend fun setLanguage(language: Language) = withIO {
        dataStore.edit { it[LANGUAGE_KEY] = language.ordinal }
    }

    suspend fun getLanguage() = withIO {
        ordinalToEnum(
            Language.values(),
            preferences.first()[LANGUAGE_KEY]
        ) ?: Language.ENGLISH
    }

    suspend fun setWorkId(workId: UUID) = withIO {
        dataStore.edit { it[WORK_ID_KEY] = workId.toString() }
    }

    suspend fun getWorkId(): UUID? = withIO {
        try {
            UUID.fromString(preferences.first()[WORK_ID_KEY])
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setUpdateTime(updateTime: UpdateTime) = withIO {
        dataStore.edit { it[UPDATE_TIME_KEY] = updateTime.ordinal }
    }

    suspend fun getUpdateTime() = withIO {
        ordinalToEnum(
            UpdateTime.values(),
            preferences.first()[UPDATE_TIME_KEY]
        ) ?: UpdateTime.ONE_DAY
    }

    suspend fun setLastImageUri(uri: String) = withIO {
        dataStore.edit { it[LAST_IMAGE_URI_KEY] = uri }
    }

    suspend fun getLastImageUri() = withIO {
        preferences.first()[LAST_IMAGE_URI_KEY]?.let { Uri.parse(it) }
    }

}