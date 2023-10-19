package com.example.rest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memeTemplatesFragment = this.supportFragmentManager.findFragmentById(R.id.meme_template_fragment_container)
        // if fragment DNE, create it
        if (memeTemplatesFragment == null){
            val frag = MemeTemplatesFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.meme_template_fragment_container, frag)
                .commit()
        }

        val renderedMemeFragment = this.supportFragmentManager.findFragmentById(R.id.rendered_meme_fragment_container)
        if (renderedMemeFragment == null){
            val frag = RenderedMemeFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.rendered_meme_fragment_container, frag)
                .commit()
        }
    }
}