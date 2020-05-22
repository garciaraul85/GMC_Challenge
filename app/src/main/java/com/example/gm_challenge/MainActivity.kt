package com.example.gm_challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.gm_challenge.data.Element
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
        displayView(null)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDrawerItemSelected(element: Element) {
        displayView(element)
    }

    private fun displayView(element: Element?) {
        title = getString(R.string.nav_item_two)

        if (savedInstanceState == null) {
            val fragment: androidx.fragment.app.Fragment?
            fragment = ItemFragment(element)
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