package com.doubleclick.widdingkmm.android

import android.Manifest
import android.app.Application
import android.content.Context
import android.webkit.PermissionRequest
import androidx.multidex.MultiDex
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider

/**
 * Created By Eslam Ghazy on 7/7/2022
 */
class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        EmojiManager.install(GoogleEmojiProvider())
        MultiDex.install(this)
        Dexter.withContext(this).withPermissions(
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {}
            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                p1: PermissionToken?
            ) {
            }
        }).check()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }


}