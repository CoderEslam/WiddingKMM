package com.doubleclick.widdingkmm.android.ViewHolder

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat


/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class LocationViewHolder(itemView: View, onMessageClick: OnMessageClick, myId: String) :
    BaseViewHolder(itemView), OnMapReadyCallback {
    //    private LottieAnimationView location_lotte;
    private val seen: ImageView
    private val onMessageClick: OnMessageClick
    private val myId: String
    private val time: TextView

    //    private SupportMapFragment supportMapFragment;
    private val map: MapView
    private var mGoogleMap: GoogleMap? = null
    private var mMapLocation: LatLng? = null

    @SuppressLint("UseCompatLoadingForDrawables", "SimpleDateFormat")
    fun OpenLocation(messageModel: MessageModel, position: Int) {
        time.text = SimpleDateFormat("M/d/yy, h:mm a").format(messageModel.time).toString()
        val list: List<String> =
            messageModel.message.replace("[", "").replace("]", "").replace(" ", "").split(",")
        val latLng = LatLng(list[0].toDouble(), list[1].toDouble())
        setMapLocation(latLng)
        if (messageModel.receiver == myId) {
            seen.visibility = View.INVISIBLE
        } else {
            seen.setImageDrawable(
                if (messageModel.seen=="true") itemView.context.resources.getDrawable(R.drawable.done_all) else itemView.context.resources.getDrawable(
                    R.drawable.done
                )
            )
        }
        itemView.setOnClickListener { v ->
            val popupMenu = PopupMenu(itemView.context, v)
            popupMenu.menuInflater.inflate(R.menu.text_chat_option, popupMenu.menu)
            popupMenu.menu.findItem(R.id.copy).title = "open"
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.deleteForme) {
                    onMessageClick.deleteForMe(messageModel, position)
                    return@setOnMenuItemClickListener true
                } else if (item.itemId == R.id.deleteforeveryone) {
                    if (isNetworkConnected(itemView.context)) {
                        onMessageClick.deleteForAll(messageModel, position)
                    } else {
                        Toast.makeText(
                            itemView.context,
                            "No Internet Connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@setOnMenuItemClickListener true
                } else if (item.itemId == R.id.copy) {
                    val i = Intent()
                    try {
                        val link =
                            "https://www.google.com/maps/?q=" + list[0] + "," + list[1]
                        i.action = Intent.ACTION_VIEW
                        i.data = Uri.parse(link)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.setPackage("com.android.chrome")
                        itemView.context.startActivity(i)
                    } catch (e: ActivityNotFoundException) {
                        // Chrome is probably not installed
                        // Try with the default browser
                        i.setPackage(null)
                        itemView.context.startActivity(i)
                    }
                    return@setOnMenuItemClickListener true
                } else {
                    return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }
    }

    private fun updateMapContents() {
        mGoogleMap!!.clear()
        mGoogleMap!!.addMarker(
            MarkerOptions().position(mMapLocation!!).title("location of you friend")
        )
        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mMapLocation!!, 17f))
    }

    private fun setMapLocation(latLng: LatLng) {
        mMapLocation = latLng
        // If the mapView is ready, update its content.
        if (mGoogleMap != null) {
            updateMapContents()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        MapsInitializer.initialize(itemView.context)
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.uiSettings.isRotateGesturesEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        // If we have mapView data, update the mapView content.
        if (mMapLocation != null) {
            updateMapContents()
        }
    }

    init {
        this.onMessageClick = onMessageClick
        this.myId = myId
        seen = itemView.findViewById(R.id.seen)
        time = itemView.findViewById(R.id.time)
        map = itemView.findViewById(R.id.map)
        map.onCreate(null)
        map.getMapAsync(this)
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }
}
