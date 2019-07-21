package com.cryptoworld.project.jvr.bitcoinstats.network

import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcMarketPrice
import io.reactivex.Observable

interface Repository {
    fun getDataFromApi(timeSpan: String): Observable<BtcMarketPrice>
}