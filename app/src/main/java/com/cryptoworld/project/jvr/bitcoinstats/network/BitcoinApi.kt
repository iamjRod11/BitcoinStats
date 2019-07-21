package com.cryptoworld.project.jvr.bitcoinstats.network

import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcMarketPrice
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BitcoinApi {

    @GET("market-price")
    fun getBtcMarketPrices(@Query("timespan") timespan: String,
                           @Query("format") format : String): Observable<BtcMarketPrice>
}