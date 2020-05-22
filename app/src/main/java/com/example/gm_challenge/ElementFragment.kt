package com.example.gm_challenge

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.gm_challenge.adapter.ElementAdapter
import com.example.gm_challenge.data.Element
import com.example.gm_challenge.viewmodel.ElementViewModel
import com.example.gm_challenge.viewmodel.ElementViewModelFactory
import kotlinx.android.synthetic.main.fragment_element.*
import java.util.*

class ElementFragment : androidx.fragment.app.Fragment() {

    private lateinit var adapter: ElementAdapter

    private var drawerListener: FragmentDrawerListener? = null
    private var mDrawerLayout: androidx.drawerlayout.widget.DrawerLayout? = null
    private var containerView: View? = null

    private var lastSelectedOption = -1

    lateinit var viewModel: ElementViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            drawerListener = context as FragmentDrawerListener
        } catch (e : RuntimeException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ElementViewModelFactory()
        ).get(ElementViewModel::class.java)


        savedInstanceState?.let {
            lastSelectedOption = it.getInt("lastSelectedOption", -1)
        }

        initRecycler()
    }

    private fun initRecycler() {
        adapter = ElementAdapter(lastSelectedOption) { position: Int, element: Element -> partItemClicked(position, element) }
        rv_drawer_list.adapter = adapter
        rv_drawer_list.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(activity)

        viewModel.elementLiveData.observe(this, androidx.lifecycle.Observer {
            adapter.update(it)
        })
        viewModel.getElements()
    }

    private fun partItemClicked(position: Int, element: Element) {
        lastSelectedOption = position
        drawerListener?.onDrawerItemSelected(element)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("lastSelectedOption", lastSelectedOption)
    }

    fun init(fragmentId: Int, drawerLayout: androidx.drawerlayout.widget.DrawerLayout?, toolbar: Toolbar) {
        containerView = activity?.findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        val drawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
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
        fun onDrawerItemSelected(element: Element)
    }
}