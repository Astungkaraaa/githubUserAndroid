package com.example.githubuserproject.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuserproject.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ThemeActivityTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(ThemeActivity::class.java)

    @Test
    fun testThemeSwitch() {
        onView(withId(R.id.switch_theme)).check(matches(isNotChecked()))
        onView(withId(R.id.hint_darkmode)).check(matches(withText(R.string.darkmode_dinonaktifkan)))

        onView(withId(R.id.switch_theme)).perform(click())

        onView(withId(R.id.switch_theme)).check(matches(isChecked()))
        onView(withId(R.id.hint_darkmode)).check(matches(withText(R.string.darkmode_diaktifkan)))
    }




}