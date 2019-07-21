package com.cryptoworld.project.jvr.bitcoinstats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.cryptoworld.project.jvr.bitcoinstats.views.BtcStatsActivity
import kotlinx.android.synthetic.main.activity_btc_stats.*
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BtcStatsActivityTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(BtcStatsActivity::class.java, true, false)

    val items  =arrayOf("30 Days", "60 Days", "180 Days", "1 Year")

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mActivityTestRule.launchActivity(null)
    }


    @Test
    fun testActivityInitialProperties(){
        val toolbarTitle = getInstrumentation().getTargetContext().getString(R.string.title_chart_activity)
        val desc = "Average USD market price across major bitcoin exchanges."

        Assert.assertEquals(0, mActivityTestRule.activity.interval_spinner.selectedItemPosition)
        Assert.assertEquals(items[0], mActivityTestRule.activity.interval_spinner.selectedItem)
        Assert.assertEquals(30, mActivityTestRule.activity.linechart.data.entryCount)
        onView(withId(R.id.my_toolbar)).check(matches(hasDescendant(withText(toolbarTitle))))
        onView(withId(R.id.desc_text)).check(matches(withText(desc)))

    }


    @Test
    fun testActivitySelectedItem1(){
        Espresso.onView((withId(R.id.interval_spinner))).perform(click())
        Espresso.onData(anything()).atPosition(0).perform(click())

        Espresso.onView(withId(R.id.interval_spinner)).check(matches(withSpinnerText(containsString(items[0]))))
        Assert.assertEquals(30, mActivityTestRule.activity.linechart.data.entryCount)
    }

    @Test
    fun testActivitySelectedItem2() {
        Espresso.onView((withId(R.id.interval_spinner))).perform(click())
        Espresso.onData(anything()).atPosition(1).perform(click())

        Espresso.onView(withId(R.id.interval_spinner)).check(matches(withSpinnerText(containsString(items[1]))))
        Assert.assertEquals(60, mActivityTestRule.activity.linechart.data.entryCount)
    }

    @Test
    fun testActivitySelectedItem3() {
        Espresso.onView((withId(R.id.interval_spinner))).perform(click())
        Espresso.onData(anything()).atPosition(2).perform(click())

        Espresso.onView(withId(R.id.interval_spinner)).check(matches(withSpinnerText(containsString(items[2]))))
        Assert.assertEquals(180, mActivityTestRule.activity.linechart.data.entryCount)
    }

    @Test
    fun testActivitySelectedItem4() {
        Espresso.onView((withId(R.id.interval_spinner))).perform(click())
        Espresso.onData(anything()).atPosition(3).perform(click())

        Espresso.onView(withId(R.id.interval_spinner)).check(matches(withSpinnerText(containsString(items[3]))))
        Assert.assertEquals(365, mActivityTestRule.activity.linechart.data.entryCount)
    }




}