package com.example.color_picker_app

import android.graphics.Color
import androidx.lifecycle.ViewModel

class SeekBarViewModel : ViewModel() {


    private var redProgress = 0.000
    private var blueProgress = 0.000
    private var greenProgress = 0.000
    private var currentColor: Int = Color.BLACK


    fun setTextViewRedProgress(progress: Double) {
        redProgress = progress
    }

    fun setTextViewBlueProgress(progress: Double) {
        blueProgress = progress
    }

    fun setTextViewGreenProgress(progress: Double) {
        greenProgress = progress
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
        return currentColor
    }

    fun getCurrentColor(): Int{
        return currentColor
    }

    fun setCurrentColor(savedColor : Int){
        currentColor = savedColor
    }

}



