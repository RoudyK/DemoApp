package com.demo.roudykk.demoapp.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.trySafe
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        findNavController(R.id.nav_host_fragment).addOnNavigatedListener { _, _ ->
            Handler().postDelayed({
                trySafe {
                    val fragment = nav_host_fragment?.childFragmentManager?.fragments?.getOrNull(0)
                    (fragment as BaseFragment?)?.let {
                        val hasChangedAlignmentMode = it.fabAlignmentMode != bottomAppBar.fabAlignmentMode
                        if (it.supportsFabAction) {
                            if (hasChangedAlignmentMode && bottomFab.isShown) {
                                bottomFab.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                                    override fun onHidden(fab: FloatingActionButton?) {
                                        super.onHidden(fab)
                                        bottomAppBar.fabAlignmentMode = it.fabAlignmentMode
                                        it.fabIconRes?.let { iconRes ->
                                            bottomFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, iconRes))
                                        }
                                        bottomFab.show()
                                    }
                                })
                            } else {
                                bottomAppBar.fabAlignmentMode = it.fabAlignmentMode
                                it.fabIconRes?.let { iconRes ->
                                    bottomFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, iconRes))
                                }
                                bottomFab.show()
                            }
                        } else {
                            bottomFab.hide()
                        }
                        bottomFab.setOnClickListener { _ -> it.fabAction() }
                    }
                }
            }, 100)
        }

        navigationView.setupWithNavController(findNavController(R.id.nav_host_fragment))
        setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))
        bottomAppBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.nav_watchlist -> findNavController(R.id.nav_host_fragment).navigate(R.id.watchList)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(drawerLayout, findNavController(R.id.nav_host_fragment))
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
