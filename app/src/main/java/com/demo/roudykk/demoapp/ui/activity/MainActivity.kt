package com.demo.roudykk.demoapp.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.trySafe
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
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
                        bottomAppBar.fabAlignmentMode = it.fabAlignmentMode
                        if (it.supportsFabAction) {
                            bottomFab.show()
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
