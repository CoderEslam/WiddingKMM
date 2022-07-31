package com.doubleclick.widdingkmm.android.Model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
object Client {
    private var retrofit: Retrofit? = null
    fun getClient(url: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}
