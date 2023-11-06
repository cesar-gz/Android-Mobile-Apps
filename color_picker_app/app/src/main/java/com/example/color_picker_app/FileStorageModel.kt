package com.example.color_picker_app

import android.content.Context
import java.io.File

const val TEXT_FILE_NAME1 = "saveProgressBarInput10.txt"
const val TEXT_FILE_NAME2 = "saveProgressBarInput11.txt"
const val TEXT_FILE_NAME3 = "saveProgressBarInput12.txt"
const val TEXT_FILE_NAME4 = "saveProgressBarInput13.txt"

class FileStorageModel(_context: Context) {
    private var context = _context

    private fun makeRedFile(): File {
        return File(this.context.filesDir, TEXT_FILE_NAME1)
    }

    private fun makeBlueFile(): File {
        return File(this.context.filesDir, TEXT_FILE_NAME2)
    }

    private fun makeGreenFile(): File {
        return File(this.context.filesDir, TEXT_FILE_NAME3)
    }

    private fun makeCombinedColorFile(): File {
        return File(this.context.filesDir, TEXT_FILE_NAME4)
    }

    fun saveRedTextInFile(s: String){
        val file = makeRedFile()
        file.delete()
        file.writeText(s)
    }

    fun saveBlueTextInFile(s: String){
        val file = makeBlueFile()
        file.delete()
        file.writeText(s)
    }

    fun saveGreenTextInFile(s: String){
        val file = makeGreenFile()
        file.delete()
        file.writeText(s)
    }

    fun saveCombinedColorFile(s: String){
        val file = makeCombinedColorFile()
        file.delete()
        file.writeText(s)
    }

    fun loadRedTextFromFile() : String{
        val file = makeRedFile()
        return if (file.exists()) {
            file.readText()
        } else{
            "0"
        }
    }

    fun loadBlueTextFromFile() : String{
        val file = makeBlueFile()
        return if (file.exists()) {
            file.readText()
        } else{
            "0"
        }
    }

    fun loadGreenTextFromFile() : String{
        val file = makeGreenFile()
        return if (file.exists()) {
            file.readText()
        } else{
            "0"
        }
    }

    fun loadCombinedColorFromFile() : String{
        val file = makeCombinedColorFile()
        return if (file.exists()) {
            file.readText()
        } else{
            "0"
        }
    }
}