package com.demo.roudykk.demoapp.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.trySafe
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, _, _ ->
            (bottomAppBar.behavior as BottomAppBar.Behavior).slideUp(bottomAppBar)
            // TODO: Broken on new version of material, fix later. (There's a bug with animateLayoutChanges true on Toolbar)
//            toolbar.post {
//                trySafe {
//                    /**
//                     * This fixes a bug where animateLayoutChanges in Toolbar doesn't animate
//                     * the toolbar back to its original position when the new destination doesn't
//                     * have a nav icon.
//                     */
//                    val childCount = toolbar.childCount
//                    for (i in 0..childCount) {
//                        val child = toolbar.getChildAt(i)
//                        if (child is TextView) {
//                            if (toolbar?.navigationIcon == null) {
//                                val animator = ObjectAnimator.ofInt(child, "left", toolbar.contentInsetStart)
//                                animator.duration = 300
//                                animator.start()
//                            }
//                        }
//                    }
//                }
//            }
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

        setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))
        bottomAppBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.nav_watchlist -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_watchlist)
                R.id.nav_settings -> findNavController(R.id.nav_host_fragment).navigate(R.id.settings)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(findNavController(R.id.nav_host_fragment), drawerLayout)
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
