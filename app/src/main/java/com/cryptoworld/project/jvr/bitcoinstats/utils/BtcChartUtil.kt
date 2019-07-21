package com.cryptoworld.project.jvr.bitcoinstats.utils

import com.cryptoworld.project.jvr.bitcoinstats.data.Constants
import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcValue
import com.github.mikephil.charting.data.Entry
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

object BtcChartUtil {
    private val months = arrayOf("","Jan ", "Feb ", "Mar ", "Apr ", "May ", "Jun ", "Jul ", "Aug ", "Sep ", "Oct ", "Nov ", "Dec ")

    fun formatTime(timeStamp : Float): String{
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        val date = Date(timeStamp.toLong()* 1000)
        return sdf.format(date)
    }

    fun getTimespan(position: Int):String{
        when(position){
            0 -> return Constants.TIMESPAN_30DAYS
            1 -> return Constants.TIMESPAN_60DAYS
            2 -> return Constants.TIMESPAN_180DAYS
            3 -> return Constants.TIMESPAN_YEAR
        }
        return "Unknown"
    }

    fun formatDollarAmount(amount : Float): String{
        return "$" + NumberFormat.getInstance().format(amount)
    }

    fun formatDateString(timeStamp: Float): String{
        val date = formatTime(timeStamp).split("-")
        return "on " + months[Integer.parseInt(date[1])] + date[2] + ", " + date[0]

    }

    fun createEntries(entries: List<BtcValue>):ArrayList<Entry>{
        val entryList = ArrayList<Entry>()
        for (currentEntry in entries){
            val entry = Entry(currentEntry.x, currentEntry.y)
            entryList.add(entry)
        }
        return entryList
    }



}