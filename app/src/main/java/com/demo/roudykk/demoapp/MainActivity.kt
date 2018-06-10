package com.demo.roudykk.demoapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.demo.roudykk.demoapp.api.model.Post
import com.demo.roudykk.demoapp.view.controller.PostsController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initDrawer()
        initRv()
    }

    private fun initRv() {
        postsRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        postsRv.itemAnimator = DefaultItemAnimator()
        val postsController = PostsController()
        postsRv.setController(postsController)
        OverScrollDecoratorHelper.setUpOverScroll(postsRv, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        val post = Post("wad", "Hey there !!!", Calendar.getInstance().timeInMillis, 18F, listOf(), 2, 3)
        val post1 = Post("waawdd", "Fellow human, suck my nut", Calendar.getInstance().timeInMillis, 24F, listOf(), 2, 3)
        val post2 = Post("waawddd", "This is a very big text", Calendar.getInstance().timeInMillis, 30F, listOf(), 2, 3)
        val post3 = Post("wwddd", "The sky looks nice today !!", Calendar.getInstance().timeInMillis, 20F,
                listOf("http://www.slate.com/content/dam/slate/blogs/bad_astronomy/2015/10/151027_BA_blue-sky.jpg.CROP.promo-xlarge2.jpg",
                        "http://bdfjade.com/data/out/81/5868652-sky.png",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAZR_ZNjiUKLdtgyuaY-MaL1FkUUvWgQtbFOiHELhgRwaFgw_cAA",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRs5Be5R0Jg3L7FK9veTMlTk7Ep2QYlt45hkSIXlth_AaDjDu7eQ"), 2, 3)
        val post4 = Post("waawddawdd", "Fellow human, suck my nut", Calendar.getInstance().timeInMillis, 24F, listOf(), 2, 3)
        val post5 = Post("waawawdawdddd", "This is a very big text", Calendar.getInstance().timeInMillis, 30F, listOf(), 2, 3)
        val post6 = Post("wddddad", "Hey there !!!", Calendar.getInstance().timeInMillis, 18F, listOf(), 2, 3)

        postsController.setData(listOf(post3, post, post1, post2, post4, post5, post6))
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> return true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
