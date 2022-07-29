package com.doubleclick.widdingkmm.android.`interface`

import com.doubleclick.widdingkmm.android.Model.MyResponse
import com.doubleclick.widdingkmm.android.Model.Sender
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created By Eslam Ghazy on 7/16/2022
 */
interface APIService {

    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAGgK66tw:APA91bEygfOaCTsWDvoP_ILoNpld1-RZo4g0UPFz4F2__hi0cpRQuGsVLxJZoTaoyuhWytMLxZoBvw3fvBCM8LnK34OzF5qU169mGeoIP4tuwHdvkqShjIEu6wZPTiui_OJxL8h7Wj38"
    )
    @POST("fcm/send")
    fun sendNotification(@Body body: Sender): Call<MyResponse>

}