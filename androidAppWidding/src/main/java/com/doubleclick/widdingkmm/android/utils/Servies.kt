package com.doubleclick.widdingkmm.android.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created By Eslam Ghazy on 12/20/2022
 */

fun isNetworkConnected(context: Context): Boolean =
    (context.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
            &&
            (context.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo!!.isConnected
