package com.example.rest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class RenderedMemeFragment : Fragment() {

    private lateinit var memerViewModel: MemerViewModel
    private lateinit var captionedMemeImage : ImageView
    private lateinit var captionButton : Button
    private lateinit var captionInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.memerViewModel = ViewModelProvider(this.requireActivity())[MemerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rendered_meme, container, false)

        this.captionedMemeImage = view.findViewById(R.id.captioned_meme)
        this.captionButton = view.findViewById(R.id.caption_button)
        this.captionInput = view.findViewById(R.id.caption_input)

        this.captionButton.setOnClickListener{
            val text = this.captionInput.text
            val imageTemplate = this.memerViewModel.getCurrentMemeTemplate()
            if (text == null){
                Log.d(TAG,"No text to use for caption.")
            } else if (imageTemplate == null){
                Log.d(TAG, "No template to be captioned.")
            } else {
                Log.d(TAG, "Asking to caption a meme: $imageTemplate with $text")
                this.memerViewModel.captionImage(
                    templateID = imageTemplate.id,
                    caption = text.toString()
                ).observe(
                    this.viewLifecycleOwner,
                    Observer { captionedImageResponseData ->
                        Log.d(TAG, "Fragment has noticed a captioned image $captionedImageResponseData")
                        Picasso.get()
                            .load(captionedImageResponseData.url)
                            .into(this.captionedMemeImage)
                    }
                )
            }
        }
        return view
    }
}