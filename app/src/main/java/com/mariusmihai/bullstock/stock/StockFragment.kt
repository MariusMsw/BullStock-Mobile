package com.mariusmihai.bullstock.stock

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.StockScreenBinding
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class StockFragment : BaseFragment<StockScreenBinding>() {

    //Here I have the stock that was clicked with StockMostImportantDataDto details in args.stockDetails
    private val args: StockFragmentArgs by navArgs()

    override val layout: Int
        get() = R.layout.stock_screen

    override val viewModel: StockViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }

        viewModel.chartData.observe(viewLifecycleOwner, {
            val values = ArrayList<Entry>()

            for (elem in it) {
                values.add(
                    Entry(
                        Date.from(Instant.parse(elem.period)).time.toFloat(),
                        elem.price.toFloat()
                    )
                )
            }
            setChartStyle(values)
        })
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

    fun setChartStyle(values: ArrayList<Entry>) {

        // // Chart Style // //
        binding.stockChart.setBackgroundColor(Color.WHITE)

        // disable description text
        binding.stockChart.description.isEnabled = false

        // enable touch gestures
        binding.stockChart.setTouchEnabled(true)

        // set listeners
        binding.stockChart.setDrawGridBackground(false)

        // enable scaling and dragging
        binding.stockChart.isDragEnabled = true
        binding.stockChart.setScaleEnabled(true)

        // force pinch zoom along both axis
        binding.stockChart.setPinchZoom(true)

        val xAxis = binding.stockChart.xAxis
        xAxis.valueFormatter = object : DefaultAxisValueFormatter(0) {
            override fun getFormattedValue(value: Float): String {
                val dateFormat = SimpleDateFormat("dd MMM", Locale.US)
                return dateFormat.format(value)
            }
        }
        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f)

        val yAxis = binding.stockChart.axisLeft

        // disable dual axis (only use LEFT axis)
        binding.stockChart.axisRight.isEnabled = false

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)

        // axis range
//        yAxis.axisMaximum = values.maxOf { it.y }
//        yAxis.axisMinimum = values.minOf { it.y }

        val llXAxis = LimitLine(9f);

        llXAxis.lineWidth = 4f;
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.textSize = 10f;
//        llXAxis.setTypeface(tfRegular);

        val ll1 = LimitLine(150f);
        ll1.lineWidth = 4f;
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.textSize = 10f;
//        ll1.setTypeface(tfRegular);

        val ll2 = LimitLine(-30f);
        ll2.lineWidth = 4f;
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.textSize = 10f;
//        ll2.setTypeface(tfRegular);

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawLimitLinesBehindData(true);

        // add limit lines
//        yAxis.addLimitLine(ll1);
//        yAxis.addLimitLine(ll2);
        //xAxis.addLimitLine(llXAxis);

        setData(values)

        // draw points over time
        binding.stockChart.animateX(1500);

    }

    private fun setData(values: ArrayList<Entry>) {

        val set1: LineDataSet
        if (binding.stockChart.data != null &&
            binding.stockChart.data.dataSetCount > 0
        ) {
            set1 = binding.stockChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            binding.stockChart.data.notifyDataChanged()
            binding.stockChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "")
//            set1.setDrawIcons(false)

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

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider ->
                    binding.stockChart.axisLeft.axisMinimum
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
            binding.stockChart.data = data
        }
    }

}