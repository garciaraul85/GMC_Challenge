package com.example.gm_challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.gm_challenge.R
import com.example.gm_challenge.data.Element
import com.example.gm_challenge.view.fragment.ElementFragment
import com.example.gm_challenge.view.fragment.ItemFragment
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

        setupToolbar()

        setupDrawer()

        this.savedInstanceState = savedInstanceState
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupDrawer() {
        drawerFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as ElementFragment
        drawerFragment.setupDrawer(R.id.fragment_navigation_drawer, drawer_layout, toolbar)
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
        val fragment: Fragment?
        fragment = ItemFragment()
        title = getString(R.string.nav_item_two)

        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putParcelable(ELEMENT, element)
            fragment.setArguments(bundle)

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_content, fragment)
            fragmentTransaction.commit()
            toolbarTitle.text = title
        }
        savedInstanceState = null
    }

    private fun isTablet(): Boolean {
        return resources.configuration.smallestScreenWidthDp >= 900
    }

    companion object {
        const val ELEMENT = "element"
    }

}