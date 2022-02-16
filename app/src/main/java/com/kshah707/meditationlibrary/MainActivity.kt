package com.kshah707.meditationlibrary

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootTopic = MeditationTopic("root", null, listOf(
            MeditationTopic("Calm My Emotions", null, listOf(
                MeditationTopic("from Work Stress", null, null, "google.com"),
                MeditationTopic("from Social Situations", null, null, "google.com"),
            ), null),
            MeditationTopic("Narrow My Focus", null, listOf(
                MeditationTopic("for One Hard Task", null, null, "google.com"),
                MeditationTopic("for Many Easy Tasks", null, null, "google.com"),
            ), null),
            MeditationTopic("Widen My Focus", null, listOf(
                MeditationTopic("for Creativity", null, null, "google.com"),
                MeditationTopic("for Playfulness", null, null, "google.com"),
            ), null),
            MeditationTopic("Try Something New", null, null, "youtube.com"),
        ), null)
        var currentRoot = rootTopic

        // Populate starting layout
        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout)
        if (currentRoot.children.isNullOrEmpty()) {
            // TODO handle case
        } else {
            for (topic in currentRoot.children!!) {
                val newBtn = Button(this)
                newBtn.text = topic.name
                buttonLayout.addView(newBtn)
            }
        }

        // TODO:
        // piece by piece. MVP as fast as possible.
        // in the constructor:
        // maintain currentRoot var (= root)
        // add function that does this and call it:
        //    for children, map each to buttons (name, imgUrl, onClick target) and display in view
        // bind onClick, either open destinationUrl or if has children, set currentRoot to that child
        // and call function to recreate layout (or go to new activity/screen with that layout)
        // refine choices list, add images, style
        // write article
    }
}

// can be nested
class MeditationTopic(
    val name: String,
    val imageUri: String?, // TODO make non nullable?
    // either has children OR destinationUrl
    var children: List<MeditationTopic>?,
    var destinationUrl: String?
    )
{
    private var parent: MeditationTopic? = null

    fun MeditationTopic() {
        if (children.isNullOrEmpty()) {
            for (child in children!!) {
                child.parent = this
            }
        }
    }
}