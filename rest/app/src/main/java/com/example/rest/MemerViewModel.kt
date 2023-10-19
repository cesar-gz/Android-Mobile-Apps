package com.example.rest

import androidx.lifecycle.ViewModel

class MemerViewModel : ViewModel() {
    private var templateIndex = 0

    fun setTemplateIndex(index : Int) {
        this.templateIndex = index
    }

    fun getTemplateIndex() : Int{
        return this.templateIndex
    }
}