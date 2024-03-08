package com.example.githubuserproject.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuserproject.R
import com.example.githubuserproject.adapter.UserAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainToTheme() {
        onView(withId(R.id.keTema)).perform(click())
        onView(withId(R.id.maintheme)).check(matches(isDisplayed()))
    }

    @Test
    fun mainToFav() {
        onView(withId(R.id.keFav)).perform(click())
        onView(withId(R.id.mainfav)).check(matches(isDisplayed()))
    }

    @Test
    fun mainToThemeBackToMain(){
        onView(withId(R.id.keTema)).perform(click())
        onView(withId(R.id.maintheme)).check(matches(isDisplayed()))
        onView(withId(R.id.backbtntheme)).perform(click())
        onView(withId(R.id.mainmain)).check(matches(isDisplayed()))
    }

    @Test
    fun mainToFavBackToMain(){
        onView(withId(R.id.keFav)).perform(click())
        onView(withId(R.id.mainfav)).check(matches(isDisplayed()))
        onView(withId(R.id.backbtnfav)).perform(click())
        onView(withId(R.id.mainmain)).check(matches(isDisplayed()))
    }

    @Test
    fun clickRecyclerViewItem_opensDetailActivity() {
        val scenario = activityRule.scenario

        // Idling Resource untuk RecyclerView
        val idlingResource = object : IdlingResource {
            private var resourceCallback: IdlingResource.ResourceCallback? = null

            override fun getName(): String {
                return "RecyclerViewIdlingResource"
            }

            override fun isIdleNow(): Boolean {
                var idle = false

                scenario.onActivity { activity ->
                    val recyclerView = activity.findViewById<RecyclerView>(R.id.rvListuserMain)
                    val adapter = recyclerView.adapter
                    idle = adapter != null && adapter.itemCount > 0
                    if (idle && resourceCallback != null) {
                        resourceCallback?.onTransitionToIdle()
                    }
                }
                return idle
            }

            override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
                resourceCallback = callback
            }
        }

        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.rvListuserMain))
            .perform(actionOnItemAtPosition<UserAdapter.MyViewHolder>(20, click()))

        onView(withId(R.id.maindetail)).check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}