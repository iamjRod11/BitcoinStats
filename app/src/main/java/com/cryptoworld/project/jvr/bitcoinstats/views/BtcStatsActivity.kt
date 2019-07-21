package com.cryptoworld.project.jvr.bitcoinstats.views

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.cryptoworld.project.jvr.bitcoinstats.R
import com.cryptoworld.project.jvr.bitcoinstats.data.Constants
import com.cryptoworld.project.jvr.bitcoinstats.data.models.BtcValue
import com.cryptoworld.project.jvr.bitcoinstats.databinding.ActivityBtcStatsBinding
import com.cryptoworld.project.jvr.bitcoinstats.utils.ChartFormatter
import com.cryptoworld.project.jvr.bitcoinstats.viewmodels.BtcStatsViewModel
import com.cryptoworld.project.jvr.bitcoinstats.viewmodels.BtcStatsViewModelFactory
import com.cryptoworld.project.jvr.bitcoinstats.views.custom.CustomMarkerView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class BtcStatsActivity : BaseActivity(), AdapterView.OnItemSelectedListener{
    @Inject
    lateinit var viewModelFactory: BtcStatsViewModelFactory
    lateinit var viewModel: BtcStatsViewModel
    private lateinit var binding : ActivityBtcStatsBinding
    companion object {
        val TAG : String? = BtcStatsActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_stats)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BtcStatsViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.getBtcMarketPrices(0)

        addToolbar()
        addSpinner()
        observeViewModel()
        setChartProperties()

    }

    private fun addToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setTitle(getString(R.string.title_chart_activity))
    }

    private fun addSpinner(){
        binding.intervalSpinner.onItemSelectedListener = this
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (isNetworkAvailable()){
            viewModel.getBtcMarketPrices(position)
        }else{
            generalErrorMessage()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(TAG, "nothing selected")
    }

    private fun observeViewModel(){
        val chartDataObserver = Observer<Boolean>{ dataReady ->
            if (dataReady){
                binding.linechart.invalidate()
                setChartData(viewModel.entryList)
            }
        }
        val errorObserver = Observer<Boolean>{ isError ->
            if (isError){
                generalErrorMessage()
                viewModel.isError.value = false
            }
        }
        viewModel.chartDataReady.observe(this, chartDataObserver)
        viewModel.isError.observe(this,errorObserver)
    }

    private fun generalErrorMessage() {
        Snackbar.make(binding.root, getString(R.string.general_error),Snackbar.LENGTH_LONG).show()
    }

    private fun setChartProperties(){
        binding.linechart.setBackgroundColor(Color.WHITE)
        binding.linechart.getDescription().setEnabled(false)
        binding.linechart.setTouchEnabled(true)
        binding.linechart.setDrawGridBackground(false)
        // create marker to display box when values are selected
        val mv = CustomMarkerView(this, R.layout.custom_marker_view)
        mv.setChartView(binding.linechart)
        binding.linechart.setMarker(mv)
        binding.linechart.setDragEnabled(true)
        binding.linechart.setScaleEnabled(true)
        binding.linechart.setPinchZoom(true)

        chartAxis()

    }

    private fun chartAxis(){
        //x-axis style
        val xAxis = binding.linechart.getXAxis()
        val xAxisFormatter = ChartFormatter()
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = xAxisFormatter
        //y-axis style
        val yAxis = binding.linechart.getAxisLeft()
        binding.linechart.getAxisRight().setEnabled(false)
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setAxisMaximum(16000f)
        yAxis.setAxisMinimum(0f)

        // draw points over time
        binding.linechart.animateX(1500)
        // get the legend (only possible after setting data)
        val l = binding.linechart.getLegend()
        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE)
    }



    private fun setChartData(values: List<Entry>) {

        val set1: LineDataSet

        if (binding.linechart.getData() != null && binding.linechart.getData().getDataSetCount() > 0) {
            set1 = (binding.linechart.getData().getDataSetByIndex(0) as LineDataSet)
            set1.values = values
            set1.notifyDataSetChanged()
            binding.linechart.getData().notifyDataChanged()
            binding.linechart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, Constants.BTC_MARKET_PRICE)
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 1f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 0f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                    IFillFormatter { dataSet, dataProvider -> binding.linechart.getAxisLeft().getAxisMinimum() }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            binding.linechart.setData(data)
        }
    }

}