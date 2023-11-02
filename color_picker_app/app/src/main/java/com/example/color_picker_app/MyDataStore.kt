package com.example.color_picker_app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val DATASTORE_TAG = "My Data Store"

class MyDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    val red_sb: Flow<Double> = this.dataStore.data.map { prefs ->
        prefs[RED_SEEK_BAR_PROGRESS] ?: 0.000
    }.distinctUntilChanged()

    val red_switch: Flow<Boolean> = this.dataStore.data.map { prefs ->
        prefs[RED_SWITCH] ?: false
    }.distinctUntilChanged()

    val red_val: Flow<Double> = this.dataStore.data.map { prefs->
        prefs[RED_VALUE] ?: 0.000
    }.distinctUntilChanged()

    private suspend fun saveDoubleValueOfSB(key: Preferences.Key<Double>, value: Double){
        this.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private suspend fun saveBoolValue(key: Preferences.Key<Boolean>, value:Boolean){
        this.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private suspend fun saveDoubleValueOfTextView(key: Preferences.Key<Double>, value: Double){
        this.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun saveDoubleInputOfSB(value: Double){
        saveDoubleValueOfSB(RED_SEEK_BAR_PROGRESS, value)
    }

    suspend fun saveBoolInput(value: Boolean){
        saveBoolValue(RED_SWITCH, value)
    }

    suspend fun saveDoubleInputOfTextView(value: Double){
        saveDoubleValueOfTextView(RED_VALUE, value)
    }

    companion object{
        private const val PREFERENCES_DATA_FILE_NAME = "MyDataStoreFile"

        private val RED_SEEK_BAR_PROGRESS = doublePreferencesKey("red_sb")
        private val BLUE_SEEK_BAR_PROGRESS = doublePreferencesKey("blue_sb")
        private val GREEN_SEEK_BAR_PROGRESS = doublePreferencesKey("green_sb")
        private val RED_SWITCH = booleanPreferencesKey("red_switch")
        private val BLUE_SWITCH = booleanPreferencesKey("blue_switch")
        private val GREEN_SWITCH = booleanPreferencesKey("green_switch")
        private val RED_VALUE = doublePreferencesKey("red_val")
        private val BLUE_VALUE = doublePreferencesKey("blue_val")
        private val GREEN_VALUE = doublePreferencesKey("green_val")

        private var Single_Instance_of_App : MyDataStore? = null

        fun initialize(context: Context){
            Log.d(DATASTORE_TAG,"Initializing single instance of App")

            if(Single_Instance_of_App == null){
                val dataStore = PreferenceDataStoreFactory.create{
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                Single_Instance_of_App = MyDataStore(dataStore)
            }
        }

        fun getRepository():MyDataStore{
            Log.d(DATASTORE_TAG, "Getting My Data Store")

            return Single_Instance_of_App ?: throw IllegalStateException("The Single Instance of the App is not yet initialized.")
        }
    }
}