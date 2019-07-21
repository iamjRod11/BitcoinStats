package com.cryptoworld.project.jvr.bitcoinstats.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.cryptoworld.project.jvr.bitcoinstats.R
import com.cryptoworld.project.jvr.bitcoinstats.viewmodels.BtcStatsViewModel
import com.cryptoworld.project.jvr.bitcoinstats.viewmodels.BtcStatsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}
