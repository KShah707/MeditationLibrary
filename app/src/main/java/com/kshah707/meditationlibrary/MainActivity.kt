package com.kshah707.meditationlibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

// Define meditation topic tree
val rootTopic = MeditationTopic("_", null, listOf(
    MeditationTopic("Calm My Emotions", null, listOf(
        MeditationTopic("from Work Stress", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
        MeditationTopic("from Social Situations", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
    ), null),
    MeditationTopic("Narrow My Focus", null, listOf(
        MeditationTopic("for One Hard Task", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
        MeditationTopic("for Many Easy Tasks", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
    ), null),
    MeditationTopic("Widen My Focus", null, listOf(
        MeditationTopic("for Creativity", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
        MeditationTopic("for Playfulness", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
    ), null),
    MeditationTopic("Try Something New", null, listOf(), "https://youtu.be/67hw7hj_xkc"),
), null)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set root as current topic
        var currentRoot = rootTopic

        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)
        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout)

        // draw buttons based on current root's children
        fun renderButtons(btnOnClick: (Button) -> Unit) {
            // clear existing buttons
            buttonLayout.removeAllViews()

            // display children buttons
            for (child in currentRoot.children) {
                // create and add a new button for each subtopic
                val newBtn = Button(this)
                newBtn.text = child.name
                newBtn.setOnClickListener { view ->
                    val btn = view as Button
                    btnOnClick(btn)
                }
                buttonLayout.addView(newBtn)
            }

            // display back button if possible
            if (currentRoot.parent != null) {
                val backBtn = Button(this)
                backBtn.text = "Go Back"
                backBtn.setOnClickListener {
                    currentRoot = currentRoot.parent!!
                    renderButtons(btnOnClick)
                }
                buttonLayout.addView(backBtn)
            }
        }

        // Define topic buttons' onClick handler
        fun topicBtnOnClick(btn: Button) {
            // currentRoot is now clicked child
            currentRoot = currentRoot.getChildByName(btn.text as String)!!

            if (currentRoot.children.isEmpty()) {
                // Go to YouTube link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentRoot.destinationUrl))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                // display children as next level of navigation
                renderButtons(::topicBtnOnClick)
            }
        }

        // populate starting layout with initial nodes
        renderButtons(::topicBtnOnClick)

        // TODO: remaining
        // refine topic tree with links
        // add images, style
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
    var parent: MeditationTopic? = null

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