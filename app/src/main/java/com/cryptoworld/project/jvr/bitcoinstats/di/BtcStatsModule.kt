package com.cryptoworld.project.jvr.bitcoinstats.di

import com.cryptoworld.project.jvr.bitcoinstats.network.BtcRepository
import com.cryptoworld.project.jvr.bitcoinstats.utils.AppSchedulerProvider
import com.cryptoworld.project.jvr.bitcoinstats.viewmodels.BtcStatsViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class BtcStatsModule {

    @Provides
    fun provideViewModelFactory(repository: BtcRepository, schedulerProvider: AppSchedulerProvider)=
        BtcStatsViewModelFactory(repository, schedulerProvider)
}