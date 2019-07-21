package com.cryptoworld.project.jvr.bitcoinstats.di

import android.app.Application
import com.cryptoworld.project.jvr.bitcoinstats.BtcStatsApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,BitcoinAppModule::class, BuildersModule::class))
interface BitcoinAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(app: Application): Builder
        fun build(): BitcoinAppComponent
    }


    fun inject(app: BtcStatsApp)


}