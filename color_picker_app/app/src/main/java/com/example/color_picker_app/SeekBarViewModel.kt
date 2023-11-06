package com.example.color_picker_app

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val VIEW_MODEL_TAG = "My View Model"

class SeekBarViewModel : ViewModel() {

    private var redProgress = 0.000
    private var blueProgress = 0.000
    private var greenProgress = 0.000
    private var currentColor: Int = Color.BLACK

    private val prefs = MyDataStore.getDataStore()
    private fun saveRedSeekBarInput(d : Double) {
        viewModelScope.launch {
            prefs.saveInputOfRedSB(d)
            Log.v(VIEW_MODEL_TAG, "Done saving Red input:$d")
        }
    }

    private fun saveBlueSeekBarInput(d : Double) {
        viewModelScope.launch {
            prefs.saveInputOfBlueSB(d)
            Log.v(VIEW_MODEL_TAG, "Done saving Blue input:$d")
        }
    }

    private fun saveGreenSeekBarInput(d : Double) {
        viewModelScope.launch {
            prefs.saveInputOfGreenSB(d)
            Log.v(VIEW_MODEL_TAG, "Done saving Green input:$d")
        }
    }

    private fun saveCombinedColorInput(i : Int) {
        viewModelScope.launch {
            prefs.saveInputOfCombinedColor(i)
            Log.v(VIEW_MODEL_TAG, "Done saving Combined Color input:$i")
        }
    }

    fun loadInputs(){
        GlobalScope.launch {
            prefs.redSBP.collectLatest {
                redProgress = it
                Log.d(VIEW_MODEL_TAG, "Loaded Red input:$redProgress")
            }
        }
        GlobalScope.launch {
            prefs.blueSBP.collectLatest {
                blueProgress = it
                Log.d(VIEW_MODEL_TAG, "Loaded Blue input:$blueProgress")
            }
        }
        GlobalScope.launch {
            prefs.greenSBP.collectLatest {
                greenProgress = it
                Log.d(VIEW_MODEL_TAG, "Loaded Green input:$greenProgress")
            }
        }
        GlobalScope.launch {
            prefs.combinedScreenColor.collectLatest {
                currentColor = it
                Log.d(VIEW_MODEL_TAG, "Loaded Combined Color input:$currentColor")
            }
        }
    }

    fun setTextViewRedProgress(progress: Double) {
        redProgress = progress
        saveRedSeekBarInput(progress)
    }

    fun setTextViewBlueProgress(progress: Double) {
        blueProgress = progress
        saveBlueSeekBarInput(progress)
    }

    fun setTextViewGreenProgress(progress: Double) {
        greenProgress = progress
        saveGreenSeekBarInput(progress)
    }

    fun getRedProgress() : Double{
        return redProgress
    }

    fun getBlueProgress() : Double{
        return blueProgress
    }

    fun getGreenProgress() : Double{
        return greenProgress
    }

    fun updateColorView() : Int{
        val red = (redProgress * 255).toInt()
        val blue = (blueProgress * 255).toInt()
        val green = (greenProgress * 255).toInt()
        currentColor = Color.rgb(red,green,blue)
        setCurrentColor(currentColor)
        return currentColor
    }

    fun getCurrentColor(): Int{
        return currentColor
    }

    fun setCurrentColor(savedColor : Int){
        currentColor = savedColor
        saveCombinedColorInput(savedColor)
    }

}



