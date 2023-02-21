package com.doubleclick.widdingkmm.android.Game.Fragment

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.doubleclick.widdingkmm.android.R
import java.util.*
import kotlin.collections.ArrayList


class GameFinishedActivity : AppCompatActivity() {

    private lateinit var array: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_finished)
        array = intent.extras!!.getStringArrayList("array") as ArrayList<String>
        Log.e("GameFinishedActivity", "onCreate: $array")
        val images = arrayOf(
            R.drawable.hakimi,
            R.drawable.hennery,
            R.drawable.iman_bani,
            R.drawable.jeneffer,
            R.drawable.justen_bieber,
            R.drawable.ladygaga,
            R.drawable.manal,
            R.drawable.messi,
            R.drawable.tamer_hosenu
        )
        val r = Random()
        val result: Int = r.nextInt(8)
        val random_image: ImageView = findViewById(R.id.random_image);
        random_image.setImageDrawable(resources.getDrawable(images[result]))
    }
}