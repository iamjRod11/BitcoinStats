package com.cryptoworld.project.jvr.bitcoinstats.views.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import com.cryptoworld.project.jvr.bitcoinstats.R
import com.cryptoworld.project.jvr.bitcoinstats.utils.BtcChartUtil
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import java.text.NumberFormat


@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView
    private val tvContent2: TextView

    init {

        tvContent = findViewById(R.id.tvContent)
        tvContent2 = findViewById(R.id.tvContent2)

    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        tvContent.text  = BtcChartUtil.formatDollarAmount(e!!.y)
        tvContent2.text = BtcChartUtil.formatDateString(e.x)

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}