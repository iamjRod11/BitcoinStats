package com.cryptoworld.project.jvr.bitcoinstats.di;

import com.cryptoworld.project.jvr.bitcoinstats.views.BtcStatsActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = BtcStatsModule.class)
    abstract BtcStatsActivity bindBtcStatsActivity();

    // Add bindings for other sub-components here
}
