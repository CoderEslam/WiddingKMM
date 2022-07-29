package com.doubleclick.widdingkmm.android

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
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.databinding.ActivityHomeBinding
import com.doubleclick.widdings.Adapters.ChatListAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
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

        val testR: RecyclerView = navView.getHeaderView(0).findViewById(R.id.testR);
        testR.adapter = ChatListAdapter(list);
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}