package com.cryptoworld.project.jvr.bitcoinstats.data.models


data class BtcMarketPrice(
    val id : Int,
    val status : String,
    val name : String,
    val unit : String,
    val period : String,
    val description : String,
    val values : List<BtcValue>)