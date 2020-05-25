package com.example.gm_challenge.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.gm_challenge.CustomMatchers.Companion.withDrawable
import com.example.gm_challenge.CustomMatchers.Companion.withRecyclerView
import com.example.gm_challenge.R
import com.example.gm_challenge.util.resources.EspressoIdlingResource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun onElementLoadTabletAndLandscape_clickElement_loadsItems_clickItem() {
        // launch activity
        ActivityScenario.launch(MainActivity::class.java)

        // Check element at position 0 has genre as rock
        onView(withRecyclerView(R.id.rv_drawer_list)?.atPosition(0))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText("rock"))))

        // Click on first element
        onView(withRecyclerView(R.id.rv_drawer_list)?.atPosition(0)).perform(click())

        // Check that first item at position 0 is Smells Like Teen Spirit
        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(0))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Smells Like Teen Spirit"))))

        // Check that we have the right icon

        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(0))
            .check(matches(ViewMatchers.hasDescendant(withDrawable(R.drawable.play_round))))

        // Click on item, changes icon from play to pause

        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(1)).perform(click())


        // Check item icon changes
        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(1))
            .check(matches(ViewMatchers.hasDescendant(withDrawable(R.drawable.pause_round))))
    }

    @Test
    fun onElementLoadPortrait_clickElement_loadsItems_clickItem() {
        // launch activity
        ActivityScenario.launch(MainActivity::class.java)

        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

        // Check element at position 0 has genre as rock
        onView(withRecyclerView(R.id.rv_drawer_list)?.atPosition(0))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText("rock"))))

        // Click on first element
        onView(withRecyclerView(R.id.rv_drawer_list)?.atPosition(0)).perform(click())

        // Close Drawer to click on navigation.
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close())

        // Check that first item at position 0 is Smells Like Teen Spirit
        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(0))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Smells Like Teen Spirit"))))

        // Check that we have the right icon

        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(0))
            .check(matches(ViewMatchers.hasDescendant(withDrawable(R.drawable.play_round))))

        // Click on item, changes icon from play to pause

        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(1)).perform(click())


        // Check item icon changes
        onView(withRecyclerView(R.id.rv_item_list)?.atPosition(1))
            .check(matches(ViewMatchers.hasDescendant(withDrawable(R.drawable.pause_round))))
    }

}