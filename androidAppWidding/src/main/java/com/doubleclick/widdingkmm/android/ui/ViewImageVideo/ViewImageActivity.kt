package com.doubleclick.widdingkmm.android.ui.ViewImageVideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.R
import kotlinx.android.synthetic.main.activity_view_image.*

class ViewImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)
        val builder: Zoomy.Builder = Zoomy.Builder(this).target(image)
        builder.register()
        Glide.with(this).load(intent.getStringExtra("image").toString()).into(image);
    }
}