package com.doubleclick.widdingkmm.android.ui.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.options
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity

class ImagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        addPixToActivity(R.id.container, options) {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    val intent = Intent(this, CreatePostActivity::class.java)
                    intent.putExtra("data", it.data.toString());
                    startActivity(intent)
                    finish()
                }
                PixEventCallback.Status.BACK_PRESSED -> Toast.makeText(
                    this,
                    "",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}