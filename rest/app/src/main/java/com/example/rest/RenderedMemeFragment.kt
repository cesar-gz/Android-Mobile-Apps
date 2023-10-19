package com.example.rest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class RenderedMemeFragment : Fragment() {

    private lateinit var memerViewModel: MemerViewModel
    private lateinit var captionedMemeImage : ImageView
    private lateinit var captionButton : Button
    private lateinit var captionInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.memerViewModel = ViewModelProvider(this)[MemerViewModel::class.java]
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

        return view
    }
}