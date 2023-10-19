package com.example.rest.api

import com.google.gson.annotations.SerializedName

class ImgFlipGetMemesResponseData {
    @SerializedName("memes")
    lateinit var templates: List<MemeTemplateItem>
}