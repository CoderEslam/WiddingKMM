package com.doubleclick.widdingkmm.android.Views.audio_record_view

/**
 * Created By Eslam Ghazy on 7/8/2022
 */
interface RecordingListener {
    fun onRecordingStarted()
    fun onRecordingLocked()
    fun onRecordingCompleted()
    fun onRecordingCanceled()
}