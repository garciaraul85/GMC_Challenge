package com.example.gm_challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.view.fragment.ElementFragment
import com.example.gm_challenge.view.fragment.ItemFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

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
        displayView(null)
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

    override fun onDrawerItemSelected(tag: Tag) {
        displayView(tag)
    }

    private fun displayView(tag: Tag?) {
        val fragment: Fragment?
        fragment = ItemFragment()

        if (savedInstanceState == null) {
            title = tag?.name ?: getString(R.string.last_fm)
            val bundle = Bundle()
            bundle.putParcelable(ELEMENT, tag)
            fragment.setArguments(bundle)

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_content, fragment)
            fragmentTransaction.commit()
        } else {
            title = savedInstanceState?.getString(CURRENT_TITLE, getString(R.string.last_fm)) ?: getString(R.string.last_fm)
        }
        toolbarTitle.text = title
        savedInstanceState = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_TITLE, title as String?)
    }

    private fun isTablet(): Boolean {
        return resources.configuration.smallestScreenWidthDp >= 900
    }

    companion object {
        const val ELEMENT = "element"
        const val CURRENT_TITLE = "currentTitle"
    }

}