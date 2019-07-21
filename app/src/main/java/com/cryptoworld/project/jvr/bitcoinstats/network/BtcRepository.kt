package com.cryptoworld.project.jvr.bitcoinstats.network

import com.cryptoworld.project.jvr.bitcoinstats.data.Constants
import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcMarketPrice
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BtcRepository @Inject constructor(private val apiService: BitcoinApi): Repository {


    override fun getDataFromApi(timeSpan: String):Observable<BtcMarketPrice> = apiService.getBtcMarketPrices(timeSpan, Constants.JSON_FORMAT)

}