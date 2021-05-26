package com.mariusmihai.bullstock.stock

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.StockScreenBinding


class StockFragment : BaseFragment<StockScreenBinding>() {

    //Here I have the stock that was clicked with StockMostImportantDataDto details in args.stockDetails
    private val args: StockFragmentArgs by navArgs()

    override val layout: Int
        get() = R.layout.stock_screen

    override val viewModel: StockViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChartStyle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToTrading = {
            findNavController().popBackStack()
        }

        viewModel.stock = args.stockDetails

        viewModel.retrieveStockData()

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }
    }

    fun setChartStyle() {

        // // Chart Style // //
        binding.stockChart.setBackgroundColor(Color.WHITE)

        // disable description text
        binding.stockChart.getDescription().setEnabled(false)

        // enable touch gestures
        binding.stockChart.setTouchEnabled(true)

        // set listeners
        binding.stockChart.setDrawGridBackground(false)

        // create marker to display box when values are selected
//        var mv = MyMarkerView(this, R.layout.custom_marker_view);

        // Set the marker to the binding.stockChart
//        mv.setChartView(binding.stockChart);
//        binding.stockChart.setMarker(mv);

        // enable scaling and dragging
        binding.stockChart.isDragEnabled = true
        binding.stockChart.setScaleEnabled(true)
        // binding.stockChart.setScaleXEnabled(true)
        // binding.stockChart.setScaleYEnabled(true)

        // force pinch zoom along both axis
        binding.stockChart.setPinchZoom(true)

        val xAxis = binding.stockChart.getXAxis()

        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f)

        val yAxis = binding.stockChart.getAxisLeft()

        // disable dual axis (only use LEFT axis)
        binding.stockChart.getAxisRight().setEnabled(false)

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)

        // axis range
        yAxis.setAxisMaximum(200f)
        yAxis.setAxisMinimum(-50f)

        val llXAxis = LimitLine(9f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
//        llXAxis.setTypeface(tfRegular);

        val ll1 = LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
//        ll1.setTypeface(tfRegular);

        val ll2 = LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
//        ll2.setTypeface(tfRegular);

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawLimitLinesBehindData(true);

        // add limit lines
        yAxis.addLimitLine(ll1);
        yAxis.addLimitLine(ll2);
        //xAxis.addLimitLine(llXAxis);

        setData(45, 180f)

        // draw points over time
        binding.stockChart.animateX(1500);

        // get the legend (only possible after setting data)
        val l = binding.stockChart.getLegend()

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    private fun setData(count: Int, range: Float) {
        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() - 30
            values.add(Entry(i.toFloat(), `val`))
        }
        val set1: LineDataSet
        if (binding.stockChart.getData() != null &&
            binding.stockChart.getData().getDataSetCount() > 0
        ) {
            set1 = binding.stockChart.getData().getDataSetByIndex(0) as LineDataSet
            set1.setValues(values)
            set1.notifyDataSetChanged()
            binding.stockChart.getData().notifyDataChanged()
            binding.stockChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider ->
                    binding.stockChart.getAxisLeft().getAxisMinimum()
                }

            // set color of filled area
            // drawables only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(context ?: return, R.drawable.fade_red)
            set1.fillDrawable = drawable
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            binding.stockChart.setData(data)
        }
    }

}