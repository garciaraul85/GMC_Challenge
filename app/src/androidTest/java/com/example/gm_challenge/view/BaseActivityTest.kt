package com.example.gm_challenge.view

import androidx.test.core.app.ActivityScenario
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule

open abstract class BaseActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        // launch activity
        ActivityScenario.launch(MainActivity::class.java)
    }
}