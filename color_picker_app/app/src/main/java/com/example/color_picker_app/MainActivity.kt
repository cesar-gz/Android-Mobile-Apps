package com.example.color_picker_app

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var redSwitch: Switch
    private lateinit var redSeekBar : SeekBar
    private lateinit var redTextView: TextView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var blueSwitch: Switch
    private lateinit var blueSeekBar : SeekBar
    private lateinit var blueTextView: TextView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var greenSwitch: Switch
    private lateinit var greenSeekBar : SeekBar
    private lateinit var greenTextView: TextView

    private lateinit var resetBtn : Button
    private lateinit var colorScreen : View

    private val seekBarViewModel : SeekBarViewModel by lazy {
        MyDataStore.initialize(this)
        ViewModelProvider(this)[SeekBarViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        Log.d(TAG, "OnCreate() called.")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectViews()
        setUpCallbacks()

        if (savedInstanceState != null) {
            val savedColor = savedInstanceState.getInt("currentColor", Color.BLACK)
            colorScreen.setBackgroundColor(savedColor)
            seekBarViewModel.setCurrentColor(savedColor)
        }

        this.seekBarViewModel.loadInputs()
        redSeekBar.progress = seekBarViewModel.getRedProgress().toInt()
        blueSeekBar.progress = seekBarViewModel.getBlueProgress().toInt()
        greenSeekBar.progress = seekBarViewModel.getGreenProgress().toInt()

        Log.d(TAG, "OnCreate() finished.")
    }

    private fun connectViews() {
        Log.d(TAG, "connectViews() called.")

        redSeekBar = findViewById(R.id.red_seek_bar)
        redSeekBar.max = 1000
        redSeekBar.progress = 0
        redTextView = findViewById(R.id.red_value)
        redSwitch = findViewById(R.id.red_switch)

        blueSeekBar = findViewById(R.id.blue_seek_bar)
        blueSeekBar.max = 1000
        blueSeekBar.progress = 0
        blueTextView = findViewById(R.id.blue_value)
        blueSwitch = findViewById(R.id.blue_switch)

        greenSeekBar = findViewById(R.id.green_seek_bar)
        greenSeekBar.max = 1000
        greenSeekBar.progress = 0
        greenTextView = findViewById(R.id.green_value)
        greenSwitch = findViewById(R.id.green_switch)

        resetBtn = findViewById(R.id.reset_button)
        colorScreen = findViewById(R.id.colorView)

        Log.d(TAG, "connectViews() finished.")
    }

    private fun setUpCallbacks() {
        Log.d(TAG, "setUpCallbacks() called.")

        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                if(redSwitch.isChecked){
                    val newProgress = progress / 1000.0
                    redTextView.text = "$newProgress"
                    seekBarViewModel.setTextViewRedProgress(newProgress)
                    colorScreen.setBackgroundColor(seekBarViewModel.updateColorView())
                } else{
                    redSwitch.isChecked = true
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?){}
            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })

        redSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(!isChecked){
                redTextView.text = getString(R.string.off)
                redSeekBar.progress = 0
                redSwitch.isChecked = false
            } else{
                redTextView.text = (seekBarViewModel.getRedProgress()).toString()
                redSeekBar.progress = (seekBarViewModel.getRedProgress() * 1000).toInt()
            }
        }

        blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                if(blueSwitch.isChecked){
                    val newProgress = progress / 1000.0
                    blueTextView.text = "$newProgress"
                    seekBarViewModel.setTextViewBlueProgress(newProgress)
                    colorScreen.setBackgroundColor(seekBarViewModel.updateColorView())
                } else{
                    blueSwitch.isChecked = true
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?){}
            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })

        blueSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(!isChecked){
                blueTextView.text = getString(R.string.off)
                blueSeekBar.progress = 0
                blueSwitch.isChecked = false
            } else{
                blueTextView.text = (seekBarViewModel.getBlueProgress()).toString()
                blueSeekBar.progress = (seekBarViewModel.getBlueProgress() * 1000).toInt()
            }
        }

        greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                if(greenSwitch.isChecked){
                    val newProgress = progress / 1000.0
                    greenTextView.text = "$newProgress"
                    seekBarViewModel.setTextViewGreenProgress(newProgress)
                    colorScreen.setBackgroundColor(seekBarViewModel.updateColorView())

                } else{
                    greenSwitch.isChecked = true
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?){}
            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })

        greenSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(!isChecked){
                greenTextView.text = getString(R.string.off)
                greenSeekBar.progress = 0
                greenSwitch.isChecked = false
            } else{
                greenTextView.text = (seekBarViewModel.getGreenProgress()).toString()
                greenSeekBar.progress = (seekBarViewModel.getGreenProgress() * 1000).toInt()
            }
        }

        resetBtn.setOnClickListener {
            Log.d(TAG, "Reset Button Click listener activated.")

            seekBarViewModel.setTextViewRedProgress(0.000)
            seekBarViewModel.setTextViewBlueProgress(0.000)
            seekBarViewModel.setTextViewGreenProgress(0.000)

            redTextView.text = getString(R.string.off)
            blueTextView.text = getString(R.string.off)
            greenTextView.text = getString(R.string.off)

            redSeekBar.progress = 0
            blueSeekBar.progress = 0
            greenSeekBar.progress = 0

            redSwitch.isChecked = false
            blueSwitch.isChecked = false
            greenSwitch.isChecked = false

            Log.d(TAG, "Everything was successfully reset.")
        }

        Log.d(TAG, "setUpCallbacks() finished.")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, "onSaveInstanceState() called")

        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putDouble("redProgress", this.seekBarViewModel.getRedProgress())
        savedInstanceState.putDouble("blueProgress", this.seekBarViewModel.getBlueProgress())
        savedInstanceState.putDouble("greenProgress", this.seekBarViewModel.getGreenProgress())
        savedInstanceState.putInt("currentColor", this.seekBarViewModel.getCurrentColor())

        savedInstanceState.putDouble("RedProgressBar", this.seekBarViewModel.getRedProgress())
        savedInstanceState.putDouble("BlueProgressBar", this.seekBarViewModel.getBlueProgress())
        savedInstanceState.putDouble("GreenProgressBar", this.seekBarViewModel.getGreenProgress())
        Log.i(TAG, "Seek Bar Values Saved")

        Log.i(TAG, "onSaveInstanceState() finished")
    }
}