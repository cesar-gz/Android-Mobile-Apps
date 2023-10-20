package com.example.rest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rest.api.ImgFlipCaptionedImageResponseData
import com.example.rest.api.ImgFlipExecutor
import com.example.rest.api.MemeTemplateItem

class MemerViewModel : ViewModel() {


    private var templateIndex = 0

    val memeTemplatesLiveData : LiveData<List<MemeTemplateItem>>
    init{
        Log.d(TAG, "init of memerViewModel called")
        this.memeTemplatesLiveData = ImgFlipExecutor().fetchTemplates()
    }

    fun setTemplateIndex(index : Int) {
        this.templateIndex = index
    }

    fun getTemplateIndex() : Int{
        return this.templateIndex
    }

    fun increaseTemplateIndex() : Int {
        this.setTemplateIndex(this.getTemplateIndex() + 1)
        if (this.templateIndex >= this.memeTemplatesLiveData.value?.size ?: 0) {
            this.templateIndex = 0
        }
        return templateIndex
    }

    fun decreaseTemplateIndex() : Int {
        this.setTemplateIndex(this.getTemplateIndex() - 1)
        if (this.templateIndex < 0 && (this.memeTemplatesLiveData.value?.size ?: 0) > 0 ) {
            this.templateIndex = this.memeTemplatesLiveData.value !!.size - 1
        }
        return templateIndex
    }

    fun getCurrentMemeTemplate() : MemeTemplateItem? {
        if (this.memeTemplatesLiveData.value != null &&
            this.templateIndex >= 0 && this.templateIndex <= this.memeTemplatesLiveData.value!!.size){
            return this.memeTemplatesLiveData.value!![this.templateIndex]
        }
        else {
            return null
        }
    }

    var captionedImageResponseLiveData : LiveData<ImgFlipCaptionedImageResponseData> = MutableLiveData<ImgFlipCaptionedImageResponseData>()
    fun captionImage(templateID : String, caption : String) : LiveData<ImgFlipCaptionedImageResponseData> {
        Log.d(TAG,"Received request to caption template $templateID with: $caption")
        this.captionedImageResponseLiveData = ImgFlipExecutor().captionImage(
            templateID, caption
        )
        return this.captionedImageResponseLiveData
    }

}