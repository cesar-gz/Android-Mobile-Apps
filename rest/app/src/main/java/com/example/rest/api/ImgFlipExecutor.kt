package com.example.rest.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rest.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// hard code because we are not implementing authentication, allowed to use professor's creds
private const val DO_NOT_DO_THIS_EVER_Username = "frankzk"
private const val DO_NOT_DO_THIS_EVER_Password = "ABcd12!@"

class ImgFlipExecutor {
    private val api: ImgFlipApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgflip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.api = retrofit.create(ImgFlipApi::class.java)
    }

    fun fetchTemplates() : LiveData<List<MemeTemplateItem>> {
        val responseLiveData: MutableLiveData<List<MemeTemplateItem>> = MutableLiveData()
        val imgFlipRequest: Call<ImgFlipGetMemesResponse> = this.api.fetchTemplates()

        imgFlipRequest.enqueue(object: Callback<ImgFlipGetMemesResponse> {
            override fun onResponse(
                call: Call<ImgFlipGetMemesResponse>,
                response: Response<ImgFlipGetMemesResponse>
            ) {
                Log.d(TAG, "Response received successfully from ImgFlip template endpoint.")
                val imgFlipGetMemesResponse : ImgFlipGetMemesResponse? = response.body()
                val imgFlipGetMemesResponseData : ImgFlipGetMemesResponseData? = imgFlipGetMemesResponse?.data
                var memeTemplates: List<MemeTemplateItem> = imgFlipGetMemesResponseData?.templates ?: mutableListOf()
                memeTemplates = memeTemplates.filterNot {
                    it.url.isBlank()
                }
                //Log.d(TAG, "ImgFlip templates: $memeTemplates")
                responseLiveData.value = memeTemplates
            }

            override fun onFailure(call: Call<ImgFlipGetMemesResponse>, t: Throwable) {
                Log.d(TAG, "Failed to fetch ImgFlip Meme Templates")
            }

        })

        return responseLiveData
    }

    fun captionImage(templateID : String, caption : String) : LiveData<ImgFlipCaptionedImageResponseData> {
        val responseLiveData : MutableLiveData<ImgFlipCaptionedImageResponseData> = MutableLiveData()
        val imgFlipRequest : Call<ImgFlipCaptionedImageResponse> = this.api.captionImage(
            templateID = templateID,
            caption1 = caption,
            caption2 = caption,
            username = DO_NOT_DO_THIS_EVER_Username,
            password = DO_NOT_DO_THIS_EVER_Password
        )

        Log.d(TAG, "Enqueueing a request to caption meme $templateID with $caption")
        imgFlipRequest.enqueue(object : Callback<ImgFlipCaptionedImageResponse> {
            override fun onResponse(
                call: Call<ImgFlipCaptionedImageResponse>,
                response: Response<ImgFlipCaptionedImageResponse>
            ) {
                Log.d(TAG, "Response to caption an image succeeded at ImgFlip endpoint.")
                val imgFlipCaptionedImageResponse : ImgFlipCaptionedImageResponse? = response.body()
                if (imgFlipCaptionedImageResponse?.success == true){
                    val imgFlipCaptionedImageResponseData : ImgFlipCaptionedImageResponseData = imgFlipCaptionedImageResponse.data
                    responseLiveData.value = imgFlipCaptionedImageResponseData
                    Log.d(TAG,"Got new meme url: ${imgFlipCaptionedImageResponseData.url}")
                } else{
                    Log.d(TAG, "Request failed with the message \"${imgFlipCaptionedImageResponse?.error_message}\"")
                }
            }

            override fun onFailure(call: Call<ImgFlipCaptionedImageResponse>, t: Throwable) {
                Log.d(TAG,"Request to caption an image failed.")
            }

        })

        return responseLiveData
    }

}