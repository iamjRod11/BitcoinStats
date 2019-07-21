package com.cryptoworld.project.jvr.bitcoinstats.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcMarketPrice
import com.cryptoworld.project.jvr.bitcoinstats.network.Repository
import com.cryptoworld.project.jvr.bitcoinstats.utils.BtcChartUtil.createEntries
import com.cryptoworld.project.jvr.bitcoinstats.utils.BtcChartUtil.getTimespan
import com.cryptoworld.project.jvr.bitcoinstats.utils.SchedulerProvider
import com.github.mikephil.charting.data.Entry
import io.reactivex.disposables.Disposable
import java.util.*

class BtcStatsViewModel(private val repository: Repository,
                        private val schedulerProvider: SchedulerProvider) : ViewModel(){

    companion object {
        private val TAG : String? = BtcStatsViewModel::class.java.simpleName
    }
    private lateinit var subscription: Disposable
    private lateinit var marketPrice: BtcMarketPrice
    val isLoaderVisible: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()
    val chartDataReady : MutableLiveData<Boolean> = MutableLiveData()
    val chartDescription : MutableLiveData<String> = MutableLiveData()

    var entryList = ArrayList<Entry>()


    fun getBtcMarketPrices(position : Int){
        subscription = repository.getDataFromApi(getTimespan(position))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { retrievePriceStart() }
            .doOnDispose {  }
            .subscribe(
                {result -> retrievePriceSuccess(result) },
                { retrievePriceError() }
            )
    }


    private fun retrievePriceStart(){
        isLoaderVisible.value = true
    }

    private fun retrievePriceSuccess(marketPrice: BtcMarketPrice){
        this.marketPrice = marketPrice
        isLoaderVisible.value = false

        chartDescription.value = marketPrice.description
        entryList = createEntries(marketPrice.values)
        chartDataReady.value = true

    }

    private fun retrievePriceError(){
        isLoaderVisible.value = false
        isError.value = true
    }


    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }








}