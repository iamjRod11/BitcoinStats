package com.cryptoworld.project.jvr.bitcoinstats


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cryptoworld.project.jvr.bitcoinstats.data.Constants
import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcMarketPrice
import com.cryptoworld.project.jvr.bitcoinstats.network.Repository
import com.cryptoworld.project.jvr.bitcoinstats.utils.SchedulerProvider
import com.cryptoworld.project.jvr.bitcoinstats.viewmodels.BtcStatsViewModel
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.junit.Assert
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class BtcStatsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val jsonString = "{\"status\":\"ok\",\"name\":\"Market Price (USD)\",\"unit\":\"USD\",\"period\":\"day\",\"description\":\"Average USD market price across major bitcoin exchanges.\",\"values\":[{\"x\":1560816000,\"y\":9160.0675},{\"x\":1560902400,\"y\":9147.005},{\"x\":1560988800,\"y\":9346.0725},{\"x\":1561075200,\"y\":9791.017499999998},{\"x\":1561161600,\"y\":10730.391666666666},{\"x\":1561248000,\"y\":10748.011666666667},{\"x\":1561334400,\"y\":10851.848333333333},{\"x\":1561420800,\"y\":11314.76153846154},{\"x\":1561507200,\"y\":12686.38833333333},{\"x\":1561593600,\"y\":11834.124166666668},{\"x\":1561680000,\"y\":11665.575833333334},{\"x\":1561766400,\"y\":11886.886153846155},{\"x\":1561852800,\"y\":11545.633333333331},{\"x\":1561939200,\"y\":10690.833333333336},{\"x\":1562025600,\"y\":10300.487500000001},{\"x\":1562112000,\"y\":11342.3175},{\"x\":1562198400,\"y\":11779.450833333334},{\"x\":1562284800,\"y\":11118.887499999999},{\"x\":1562371200,\"y\":11411.616666666669},{\"x\":1562457600,\"y\":11310.506666666668},{\"x\":1562544000,\"y\":11788.069166666668},{\"x\":1562630400,\"y\":12567.703846153845},{\"x\":1562716800,\"y\":12668.629166666668},{\"x\":1562803200,\"y\":11560.6025},{\"x\":1562889600,\"y\":11577.695384615385},{\"x\":1562976000,\"y\":11412.124166666668},{\"x\":1563062400,\"y\":10852.926666666668},{\"x\":1563148800,\"y\":10438.554166666667},{\"x\":1563235200,\"y\":10300.411666666667},{\"x\":1563321600,\"y\":9584.475833333332}]}"
    @Mock private lateinit var mockRepository: Repository
    @Mock private lateinit var mockSchedulerProvider: SchedulerProvider
    @InjectMocks private lateinit var btcViewModel : BtcStatsViewModel
    private lateinit var marketPrice: BtcMarketPrice
    private lateinit var mockScheduler : TestScheduler

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        marketPrice = Gson().fromJson(jsonString, BtcMarketPrice::class.java)
        mockScheduler = TestScheduler()

    }


    @Test
    fun testGetMarketPriceSuccess(){
        val desc = "Average USD market price across major bitcoin exchanges."
        Mockito.`when`(mockSchedulerProvider.io()).thenReturn(mockScheduler)
        Mockito.`when`(mockSchedulerProvider.ui()).thenReturn(mockScheduler)
        Mockito.`when`(mockRepository.getDataFromApi(Constants.TIMESPAN_30DAYS)).thenReturn(Observable.just(marketPrice))

        btcViewModel.getBtcMarketPrices(0)
        mockScheduler.triggerActions()

        Assert.assertFalse(btcViewModel.isLoaderVisible.value!!)
        Assert.assertTrue(btcViewModel.chartDataReady.value!!)
        Assert.assertEquals(desc, btcViewModel.chartDescription.value!!)
        Assert.assertEquals(30, btcViewModel.entryList.size)

    }

    @Test
    fun testGetMarketPriceFailure(){
        val e = Exception()
        Mockito.`when`(mockSchedulerProvider.io()).thenReturn(mockScheduler)
        Mockito.`when`(mockSchedulerProvider.ui()).thenReturn(mockScheduler)
        Mockito.`when`(mockRepository.getDataFromApi(Constants.TIMESPAN_30DAYS)).thenReturn(Observable.error(e))

        btcViewModel.getBtcMarketPrices(0)
        mockScheduler.triggerActions()


        Assert.assertFalse(btcViewModel.isLoaderVisible.value!!)
        Assert.assertTrue(btcViewModel.isError.value!!)

    }





}

