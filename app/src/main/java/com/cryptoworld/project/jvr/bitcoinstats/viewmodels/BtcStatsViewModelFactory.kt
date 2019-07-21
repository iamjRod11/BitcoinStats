package com.cryptoworld.project.jvr.bitcoinstats.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cryptoworld.project.jvr.bitcoinstats.network.Repository
import com.cryptoworld.project.jvr.bitcoinstats.utils.SchedulerProvider

class BtcStatsViewModelFactory(private val repository : Repository,
                               private val schedulerProvider: SchedulerProvider)  : ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BtcStatsViewModel(repository, schedulerProvider) as T
    }
}