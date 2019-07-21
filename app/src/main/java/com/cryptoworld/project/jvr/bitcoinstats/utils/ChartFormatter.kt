package com.cryptoworld.project.jvr.bitcoinstats.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class ChartFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return BtcChartUtil.formatTime(value).substring(5)

    }
}