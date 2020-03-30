package com.reactivemobile.app

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reactivemobile.app.ui.rates.RatesActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadCVTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(RatesActivity::class.java)

    @Test
    fun testCVLoadsOK() {
        onView(withId(R.id.get_cv)).perform(click())
        checkCVFieldsExist()

        rotateScreen()
        checkCVFieldsExist()
    }

    private fun checkCVFieldsExist() {
        onView(withText("Basics")).check(matches(isDisplayed()))
        onView(withText("Work")).check(matches(isDisplayed()))
    }

    private fun rotateScreen() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val orientation = context.resources.configuration.orientation

        val activity = rule.activity
        activity.requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT)
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
