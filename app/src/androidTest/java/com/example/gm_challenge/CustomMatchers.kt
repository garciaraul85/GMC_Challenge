package com.example.gm_challenge

import android.view.View
import org.hamcrest.Matcher

class CustomMatchers {
    companion object {
        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher? {
            return RecyclerViewMatcher(recyclerViewId)
        }

        fun withDrawable(resourceId: Int): Matcher<View?>? {
            return DrawableMatcher(resourceId)
        }

    }
}