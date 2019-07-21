package com.cryptoworld.project.jvr.bitcoinstats

import com.cryptoworld.project.jvr.bitcoinstats.data.Constants
import com.cryptoworld.project.jvr.bitcoinstats.utils.BtcChartUtil
import com.cryptoworld.project.jvr.bitcoinstats.utils.ChartFormatter
import org.junit.Assert
import org.junit.Test

class BtcUnitTests {

    @Test
    fun getTimespanTest(){
        Assert.assertEquals(Constants.TIMESPAN_30DAYS, BtcChartUtil.getTimespan(0))
        Assert.assertEquals(Constants.TIMESPAN_60DAYS, BtcChartUtil.getTimespan(1))
        Assert.assertEquals(Constants.TIMESPAN_180DAYS, BtcChartUtil.getTimespan(2))
        Assert.assertEquals(Constants.TIMESPAN_YEAR, BtcChartUtil.getTimespan(3))

    }


    @Test
    fun getDefaultTimeSpanTest(){
        Assert.assertEquals("Unknown", BtcChartUtil.getTimespan(99))

    }

    @Test
    fun formatDollarTest(){
        Assert.assertEquals("$10,000", BtcChartUtil.formatDollarAmount(10000f))
        Assert.assertEquals("$10,000.99", BtcChartUtil.formatDollarAmount(10000.99f))
        Assert.assertEquals("$100.45", BtcChartUtil.formatDollarAmount(100.45f))
    }

    @Test
    fun formatTimestampTest(){
        Assert.assertEquals("2018-07-16", BtcChartUtil.formatTime(1531785600f))
        Assert.assertEquals("1969-12-31", BtcChartUtil.formatTime(0f))
    }

    @Test
    fun formatDateTextTest(){
        Assert.assertEquals("on Jul 16, 2018", BtcChartUtil.formatDateString(1531785600f))
        Assert.assertEquals("on Dec 31, 1969", BtcChartUtil.formatDateString(0f))
    }

    @Test
    fun chartFormatterTest(){
        val chartFormatter = ChartFormatter()

        Assert.assertEquals("07-16", chartFormatter.getFormattedValue(1531785600f))
        Assert.assertEquals("07-21", chartFormatter.getFormattedValue(1532217600f))
        Assert.assertEquals("12-31", chartFormatter.getFormattedValue(0f))
    }
}