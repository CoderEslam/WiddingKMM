package com.doubleclick.widdingkmm.android.Views.fabfilter.main

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.crystalrangeseekbar.widgets.CrystalSeekbar
import com.google.android.material.appbar.AppBarLayout
import com.doubleclick.widdingkmm.android.Views.fabfilter.filter.FiltersLayout
import com.doubleclick.widdingkmm.android.Views.fabfilter.filter.FiltersMotionLayout
import com.doubleclick.widdingkmm.android.Views.fabfilter.utils.bindView


var animationPlaybackSpeed: Double = 0.8

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var appbar: AppBarLayout
    private lateinit var drawerIcon: View
    private lateinit var filtersLayout: FiltersLayout
    private lateinit var filtersMotionLayout: FiltersMotionLayout

    // layout/nav_drawer views
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var motionLayoutCheckbox: CheckBox
    private lateinit var animationSpeedSeekbar: CrystalSeekbar
    private lateinit var animationSpeedText: TextView
    private lateinit var githubCodeLink: TextView
    private lateinit var githubMeLink: TextView

    private lateinit var mainListAdapter: MainListAdapter
    private val loadingDuration: Long
        get() =  (resources.getInteger(R.integer.loadingAnimDuration) / animationPlaybackSpeed).toLong()

    /**
     * Used to open nav drawer when opening app for first time (to show options)
     */
    private val prefs: SharedPreferences
        get() = getSharedPreferences("FabFilter", Context.MODE_PRIVATE)
    private var isFirstTime: Boolean
        get() = prefs.getBoolean("isFirstTime", true)
        set(value) = prefs.edit { putBoolean("isFirstTime", value) }

    /**
     * Used by FiltersLayout since we don't want to expose mainListAdapter (why?)
     * (Option: Combine everything into one activity if & when necessary)
     */
    var isAdapterFiltered: Boolean
        get() = mainListAdapter.isFiltered
        set(value) {
            mainListAdapter.isFiltered = value
        }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_filter)

        recyclerView = findViewById(R.id.recycler_view)
        appbar = findViewById(R.id.appbar)
        drawerIcon = findViewById(R.id.drawer_icon)
        filtersLayout = findViewById(R.id.filters_layout)
        filtersMotionLayout = findViewById(R.id.filters_motion_layout)



        drawerLayout = findViewById(R.id.drawer_layout)
        motionLayoutCheckbox = findViewById(R.id.motion_layout_checkbox)
        animationSpeedSeekbar = findViewById(R.id.animation_speed_seekbar)
        animationSpeedText = findViewById(R.id.animation_speed_text)
        githubCodeLink = findViewById(R.id.github_code_link)
        githubMeLink = findViewById(R.id.github_me_link)

        // Appbar behavior init
        (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = ToolbarBehavior()

        // Init FilterLayout
        useFiltersMotionLayout(false)

        // RecyclerView Init
        mainListAdapter = MainListAdapter(this)
        recyclerView.adapter = mainListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        updateRecyclerViewAnimDuration()

        // Nav Drawer Init
        animationSpeedSeekbar.setOnSeekbarChangeListener { value ->
            animationPlaybackSpeed = value as Double
            animationSpeedText.text = "${"%.1f".format(animationPlaybackSpeed)}x"
            filtersMotionLayout.updateDurations()
            updateRecyclerViewAnimDuration()
        }
        drawerIcon.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        githubCodeLink.setOnClickListener { openBrowser(1) }
        githubMeLink.setOnClickListener { openBrowser(1) }
        motionLayoutCheckbox.setOnCheckedChangeListener { _, isChecked ->
            useFiltersMotionLayout(
                isChecked
            )
        }

        // Open Nav Drawer when opening app for the first time
        if (isFirstTime) {
            drawerLayout.openDrawer(GravityCompat.START)
            isFirstTime = false
        }
    }

    /**
     * Callback for motionLayoutCheckbox
     * isChecked = true -> Use [FiltersMotionLayout]
     * isChecked = false -> Use [FiltersLayout]
     */
    private fun useFiltersMotionLayout(isChecked: Boolean) {
        filtersLayout.isVisible = !isChecked
        filtersMotionLayout.isVisible = isChecked
    }

    /**
     * Update RecyclerView Item Animation Durations
     */
    private fun updateRecyclerViewAnimDuration() = recyclerView.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    /**
     * Open browser for given string resId URL
     */
    private fun openBrowser(resId: Int): Unit =
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(resId))))

    /**
     * Called from FiltersLayout to get adapter scale down animator
     */
    fun getAdapterScaleDownAnimator(isScaledDown: Boolean): ValueAnimator =
        mainListAdapter.getScaleDownAnimator(isScaledDown)
}