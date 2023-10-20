package com.example.rest.api

import com.google.gson.annotations.SerializedName

class ImgFlipCaptionedImageResponse {
    @SerializedName("success")
    var success = false

    @SerializedName("data")
    lateinit var data : ImgFlipCaptionedImageResponseData

    @SerializedName("error_message")
    lateinit var error_message : String
}