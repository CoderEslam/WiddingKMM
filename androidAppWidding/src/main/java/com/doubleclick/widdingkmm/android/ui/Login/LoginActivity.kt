package com.doubleclick.widdingkmm.android.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.widdingkmm.android.HomeActivity
import com.doubleclick.widdingkmm.android.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var listener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        try {
            auth = FirebaseAuth.getInstance();
            listener = FirebaseAuth.AuthStateListener {
                if (auth!!.currentUser != null) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }

        } catch (e: NullPointerException) {

        }

    }

    override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(listener)
    }

    override fun onStop() {
        super.onStop()
        auth!!.addAuthStateListener(listener)
    }
}