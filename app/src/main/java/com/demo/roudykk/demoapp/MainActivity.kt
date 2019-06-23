package com.demo.roudykk.demoapp

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.demo.roudykk.demoapp.extensions.trySafe
import com.demo.roudykk.demoapp.ui.activity.BaseActivity
import com.demo.roudykk.demoapp.ui.fragment.BaseFragmentBuilder
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        initDestinationListener()

        setupActionBarWithNavController(this, navController)
        navigationView.setupWithNavController(navController)

        bottomAppBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initDestinationListener() {
        navController.addOnDestinationChangedListener { _, _, _ ->
            (bottomAppBar.behavior as BottomAppBar.Behavior).slideUp(bottomAppBar)
            Handler().postDelayed({
                trySafe {
                    val fragment = nav_host_fragment?.childFragmentManager?.fragments?.getOrNull(0)
                    (fragment as BaseFragmentBuilder?)?.let {
                        val hasChangedAlignmentMode = it.fabAlignmentMode() != bottomAppBar.fabAlignmentMode
                        if (it.supportsFabAction()) {
                            if (hasChangedAlignmentMode && bottomFab.isShown) {
                                bottomFab.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                                    override fun onHidden(fab: FloatingActionButton?) {
                                        super.onHidden(fab)
                                        bottomAppBar.fabAlignmentMode = it.fabAlignmentMode()
                                        it.fabIconRes()?.let { iconRes ->
                                            bottomFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, iconRes))
                                        }
                                        bottomFab.show()
                                    }
                                })
                            } else {
                                bottomAppBar.fabAlignmentMode = it.fabAlignmentMode()
                                it.fabIconRes()?.let { iconRes ->
                                    bottomFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, iconRes))
                                }
                                bottomFab.show()
                            }
                        } else {
                            bottomFab.hide()
                        }
                        bottomFab.setOnClickListener { _ -> it.fabAction().invoke() }
                    }
                }
            }, 100)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        when (menuItem.itemId) {
            R.id.home -> navController.popBackStack(R.id.home, false)
            R.id.watchList -> navController.navigate(R.id.watchList)
            R.id.about -> navController.navigate(R.id.about)
            R.id.settings -> navController.navigate(R.id.settings)
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun appBar(): AppBarLayout? {
        return appBarLayout
    }
}
