package com.example.color_picker_app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile

private const val DATASTORE_TAG = "My Data Store"

class MyDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    companion object{
        private const val PREFERENCES_DATA_FILE_NAME = "MyDataStoreFile"
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