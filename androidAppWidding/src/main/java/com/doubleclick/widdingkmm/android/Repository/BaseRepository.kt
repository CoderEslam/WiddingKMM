package com.doubleclick.widdingkmm.android.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


/**
 * Created By Eslam Ghazy on 7/8/2022
 */
open class BaseRepository {

    var reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var id: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""


}