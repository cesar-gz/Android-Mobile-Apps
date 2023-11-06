package com.example.color_picker_app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val DATASTORE_TAG = "My Data Store"

class MyDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    private val redSeekBarProgress = doublePreferencesKey("redSBP")
    private val blueSeekBarProgress = doublePreferencesKey("blueSBP")
    private val greenSeekBarProgress = doublePreferencesKey("greenSBP")
    private val combinedColor = intPreferencesKey("combinedScreenColor")

    val redSBP: Flow<Double> = this.dataStore.data.map { prefs ->
        prefs[redSeekBarProgress] ?: 0.000
    }

    val blueSBP: Flow<Double> = this.dataStore.data.map { prefs ->
        prefs[blueSeekBarProgress] ?: 0.000
    }

    val greenSBP: Flow<Double> = this.dataStore.data.map { prefs ->
        prefs[greenSeekBarProgress] ?: 0.000
    }

    val combinedScreenColor: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[combinedColor] ?: Color.BLACK
    }

    private suspend fun saveDoubleValueOfSB(key: Preferences.Key<Double>, value: Double){
        this.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int){
        this.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun saveInputOfRedSB(value: Double){
        saveDoubleValueOfSB(redSeekBarProgress, value)
    }

    suspend fun saveInputOfBlueSB(value: Double){
        saveDoubleValueOfSB(blueSeekBarProgress, value)
    }

    suspend fun saveInputOfGreenSB(value: Double){
        saveDoubleValueOfSB(greenSeekBarProgress, value)
    }

    suspend fun saveInputOfCombinedColor(value: Int){
        saveIntValue(combinedColor, value)
    }

    companion object{
        private const val PREFERENCES_DATA_FILE_NAME = "MyDataStoreFile"
        private var Single_Instance_Of_App : MyDataStore? = null

        fun initialize(context: Context){
            Log.d(DATASTORE_TAG,"Initializing single instance of App")

            if(Single_Instance_Of_App == null){
                val dataStore = PreferenceDataStoreFactory.create{
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                Single_Instance_Of_App = MyDataStore(dataStore)
            }
        }

        fun getDataStore():MyDataStore{
            Log.d(DATASTORE_TAG, "Getting My Data Store")
            return Single_Instance_Of_App ?: throw IllegalStateException("The Single Instance of the App is not yet initialized.")
        }
    }
}