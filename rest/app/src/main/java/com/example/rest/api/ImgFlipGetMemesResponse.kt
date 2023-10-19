package com.example.rest.api

import com.google.gson.annotations.SerializedName

class ImgFlipGetMemesResponse {
    @SerializedName("data")
    lateinit var data: ImgFlipGetMemesResponseData
}