package com.example.gm_challenge.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.util.SimpleDividerItemDecoration
import com.example.gm_challenge.view.adapter.ElementAdapter
import com.example.gm_challenge.viewmodel.ElementViewModel
import kotlinx.android.synthetic.main.fragment_element.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElementFragment : androidx.fragment.app.Fragment() {

    private lateinit var adapter: ElementAdapter

    private var drawerListener: FragmentDrawerListener? = null
    private var mDrawerLayout: androidx.drawerlayout.widget.DrawerLayout? = null
    private var containerView: View? = null

    private var lastSelectedOption = -1

    private val viewModel: ElementViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            drawerListener = context as FragmentDrawerListener
        } catch (e : RuntimeException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            lastSelectedOption = it.getInt(LAST_SELECTED_OPTION, -1)
        }

        setupRecycler()
        getElementsData()
    }

    private fun setupRecycler() {
        adapter = ElementAdapter(lastSelectedOption) {
                position: Int, tag: Tag -> elementClicked(position, tag)
        }
        rv_drawer_list.adapter = adapter
        rv_drawer_list.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(activity)
    }

    private fun getElementsData() {
        viewModel.elementLiveData.observe(this, Observer {
            adapter.update(it)
        })
        viewModel.getElements()
    }

    private fun elementClicked(position: Int, tag: Tag) {
        lastSelectedOption = position
        drawerListener?.onDrawerItemSelected(tag)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_OPTION, lastSelectedOption)
    }

    fun setupDrawer(fragmentId: Int, drawerLayout: androidx.drawerlayout.widget.DrawerLayout?, toolbar: Toolbar) {
        containerView = activity?.findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        val drawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }

        mDrawerLayout?.addDrawerListener(drawerToggle)
        mDrawerLayout?.post { drawerToggle.syncState() }
    }

    interface FragmentDrawerListener {
        fun onDrawerItemSelected(tag: Tag)
    }

    companion object {
        const val LAST_SELECTED_OPTION = "lastSelectedOption"
    }

}