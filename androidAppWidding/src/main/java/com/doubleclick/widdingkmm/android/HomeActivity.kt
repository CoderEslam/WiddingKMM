package com.doubleclick.widdingkmm.android

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.ViewModel.UserViewModel
import com.doubleclick.widdingkmm.android.Views.CircleImageView
import com.doubleclick.widdingkmm.android.Views.fabfilter.filter.FiltersLayout
import com.doubleclick.widdingkmm.android.Views.fabfilter.filter.FiltersMotionLayout
import com.doubleclick.widdingkmm.android.Views.fabfilter.main.MainActivity
import com.doubleclick.widdingkmm.android.Views.fabfilter.main.MainListAdapter
import com.doubleclick.widdingkmm.android.Views.fabfilter.main.animationPlaybackSpeed
import com.doubleclick.widdingkmm.android.ui.Chat.ChatListActivity
import com.doubleclick.widdingkmm.android.ui.Profile.ProfileActivity
import com.doubleclick.widdings.Adapters.ChatListAdapter
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.main_recycler.*
import kotlinx.android.synthetic.main.nav_header_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

var options = Options();

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var myImage: CircleImageView
    private lateinit var userViewModel: UserViewModel
    private lateinit var animationView: LottieAnimationView
    private lateinit var filtersLayout: FiltersLayout
    private lateinit var filtersMotionLayout: FiltersMotionLayout
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var rv_nav_side: RecyclerView

    private lateinit var mainListAdapter: MainListAdapter
    private val loadingDuration: Long
        get() = (resources.getInteger(R.integer.loadingAnimDuration) / animationPlaybackSpeed).toLong()

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        animationView = findViewById(R.id.animationView)
        myImage = findViewById(R.id.myImage);
        filtersLayout = findViewById(R.id.filters_layout);
        filtersMotionLayout = findViewById(R.id.filters_motion_layout);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        rv_nav_side = navView.getHeaderView(0).findViewById(R.id.rv_nav_side);
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java];
        mainListAdapter = MainListAdapter(this)
        setSupportActionBar(toolbar)
        // Init FilterLayout
        useFiltersMotionLayout(false)
        updateRecyclerViewAnimDuration()

        userViewModel.getUserDate().observe(this) {
            Glide.with(this).load(it.image).into(myImage);
        }
        var list: MutableList<User> = ArrayList()
        list.add(User("", "fdhjfg", "", "", "", "", "", ""))
        list.add(User("", "dhnsdgh", "", "", "", "", "", ""))
        list.add(User("", "sejtewe", "", "", "", "", "", ""))
        list.add(User("", "dzbfsdfh", "", "", "", "", "", ""))
        list.add(User("", "sehrtwety", "", "", "", "", "", ""))
        list.add(User("", "sehttt", "", "", "", "", "", ""))
        list.add(User("", "eww4", "", "", "", "", "", ""))
        list.add(User("", "rejrtjhr", "", "", "", "", "", ""))
        list.add(User("", "we5uuyew5y", "", "", "", "", "", ""))
        list.add(User("", "w45wu4y", "", "", "", "", "", ""))
        list.add(User("", "wttyw4ty", "", "", "", "", "", ""))
        list.add(User("", "w54y45y", "", "", "", "", "", ""))
        list.add(User("", "w5y4y", "", "", "", "", "", ""))
        list.add(User("", "w3y535y", "", "", "", "", "", ""))
        list.add(User("", "wy35w53y", "", "", "", "", "", ""))
        list.add(User("", "5y523y", "", "", "", "", "", ""))
        list.add(User("", "35y3y", "", "", "", "", "", ""))
        list.add(User("", "5y35y", "", "", "", "", "", ""))
        list.add(User("", "w53y523y5", "", "", "", "", "", ""))

        mainRecycler.adapter = mainListAdapter;
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        options = Options().apply {
            ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
            count =
                30                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path =
                "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing =
                false                                       //Front Facing camera on start
            mode =
                Mode.All                                             //Option to select only pictures or videos or both
            flash =
                Flash.Auto                                          //Option to select flash type
            preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
        }
        myImage.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java));
        }

        animationView.setOnClickListener {
//            startActivity(Intent(this, ChatListActivity::class.java));
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
    private fun updateRecyclerViewAnimDuration() = mainRecycler.itemAnimator?.apply {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }


    /**
     * Called from FiltersLayout to get adapter scale down animator
     */
    fun getAdapterScaleDownAnimator(isScaledDown: Boolean): ValueAnimator =
        mainListAdapter.getScaleDownAnimator(isScaledDown)


    /*
    * reference by id of menu in navigation
    * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    /*
    * to put side navigation controller
    * */
    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}