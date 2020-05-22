package com.example.gm_challenge

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ElementFragment.FragmentDrawerListener {

    private lateinit var drawerFragment: ElementFragment
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isTablet()) {
            setContentView(R.layout.activity_main_tablet)
        } else {
            setContentView(R.layout.activity_main)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as ElementFragment
        drawerFragment.init(R.id.fragment_navigation_drawer, drawer_layout, toolbar)

        this.savedInstanceState = savedInstanceState
        displayView(0)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDrawerItemSelected(view: View, position: Int) {
        displayView(position)
    }

    private fun displayView(position: Int) {
        val fragment: Fragment?
        fragment = ItemFragment()
        title = getString(R.string.nav_item_two)

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_content, fragment)
            fragmentTransaction.commit()
            toolbarTitle.text = title
        }
    }

    private fun isTablet(): Boolean {
        return resources.configuration.smallestScreenWidthDp >= 900
    }
}