package com.kshah707.meditationlibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define meditation topic tree
        val rootTopic = MeditationTopic("root topic", null, listOf(
            MeditationTopic("Calm My Emotions", null, listOf(
                MeditationTopic("from Work Stress", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
                MeditationTopic("from Social Situations", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
            ), null),
            MeditationTopic("Narrow My Focus", null, listOf(
                MeditationTopic("for One Hard Task", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
                MeditationTopic("for Many Easy Tasks", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
            ), null),
            MeditationTopic("Widen My Focus", null, listOf(
                MeditationTopic("for Creativity", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
                MeditationTopic("for Playfulness", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
            ), null),
            MeditationTopic("Try Something New", null, listOf(), "https://www.youtube.com/watch?v=bzSTpdcs-EI"),
        ), null)

        // set root as current topic
        var currentRoot = rootTopic

        // Populate starting layout
        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout)
        // Define onClick handler
        fun btnOnClick(view: View?) {
            val btn = view as Button

            buttonLayout.removeAllViews()
            if (currentRoot.children.isEmpty()) {
                // FIXME Go to youtube link
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentRoot.destinationUrl));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setPackage("com.google.android.youtube");
//                startActivity(intent)

                // FIXME temp test, move back
                // internally move up to parent
                currentRoot = currentRoot.parent

                // display parent's children as next level of navigation
                for (child in currentRoot.children) {
                    val newBtn = Button(this)
                    newBtn.text = child.name
                    newBtn.setOnClickListener { v -> btnOnClick(v) }
                    buttonLayout.addView(newBtn)
                }
            } else {
                // internally move down to a child
                currentRoot = currentRoot.getChildByName(btn.text as String)!!

                // display child's children as next level of navigation
                for (child in currentRoot.children) {
                    val newBtn = Button(this)
                    newBtn.text = child.name
                    newBtn.setOnClickListener { v -> btnOnClick(v) }
                    buttonLayout.addView(newBtn)
                }
            }
        }

        // populate starting layout with initial nodes
        for (child in currentRoot.children) {
            // create a Button to display the topic
            val newBtn = Button(this)
            newBtn.text = child.name

            // set onClick
            newBtn.setOnClickListener {v -> btnOnClick(v)}
            buttonLayout.addView(newBtn)
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
    val imageUri: String?,
    var children: List<MeditationTopic>,
    var destinationUrl: String?
    )
{
    lateinit var parent: MeditationTopic

    init {
        for (child in children) {
            child.parent = this
        }
    }

    fun getChildByName(name: String): MeditationTopic? {
        for (child in children) {
            if (child.name == name) {
                return child
            }
        }
        // if no child matches
        return null
    }
}