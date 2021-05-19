package snow.app.eduhub.ui.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.Gson
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentTestScoreBinding
import snow.app.eduhub.ui.adapter.SubScoreListingAdapter
import snow.app.eduhub.ui.network.responses.scoreres.TestListing
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.viewmodels.TestScoreFragmentVm
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TestScoreFragment : BaseFragment() {
    lateinit var viewModel: TestScoreFragmentVm
    lateinit var binding: FragmentTestScoreBinding
    var x: ArrayList<Entry>? = null
    var y: ArrayList<String>? = null
    val values = java.util.ArrayList<Entry>()

    companion object {
        var testnamearray = ArrayList<String>()
    }

    var Token: String? = null
    private var mChart: LineChart? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // val view=inflater.inflate(R.layout.fragment_test_score, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_score, container, false);
        binding.lifecycleOwner = this
        viewModel = TestScoreFragmentVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)

        viewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel.isError.observe(requireActivity(), Observer {
            if (it.isError) {
                showError(it.message, requireContext());
            }

        })


        binding.edStartdate.setOnClickListener {
            showDatePickerDialog(binding.edStartdate)
        }

        binding.edEndDate.setOnClickListener {
            showDatePickerDialog(binding.edEndDate)
        }

        viewModel.respData.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    testnamearray.clear()

                    /*       if(it.data.testListing.size==0){
                               binding.noRecordFound.visibility=View.VISIBLE
                           }else {
                               binding.noRecordFound.visibility = View.GONE
       */


                    binding.chart.clear()
                    for (i in 0 until it.data.testListing.size) {
                        testnamearray.add(it.data.testListing.get(i).testName)
                    }
                    setDummyDataOnChart(it.data.testListing, binding.root)

                    //  }
                    binding.tvAverage.setText("" + it.data.averageCurrentWeek)
                    binding.tvWeek.setText(
                        "" + it.data.currentAttendTestCount +
                                " out of " + it.data.currentWeekTestCount + " test you have completed in this week")


                    binding.customProgressBar.progress = it.data.currentAttendTestCount
                    binding.customProgressBar.max = it.data.currentWeekTestCount
                    //set score adapter
                    val linearLayoutManagertut = GridLayoutManager(requireContext(), 2)
                    binding.rvScorelisting.layoutManager = linearLayoutManagertut
                    val chaptersAdapter =
                        SubScoreListingAdapter(requireContext(), it.data.scoreListing)
                    binding.rvScorelisting.adapter = chaptersAdapter


                } else {
                    dialog.dismiss()
                    Log.e("statusfalse", "login--")
                    showError(it.message, requireActivity())
                }
            }

        })




        binding.done.setOnClickListener {

            if (isNetworkConnected()) {
                if (binding.edStartdate.text.toString().isEmpty()) {
                    showToast("Please enter start date")
                } else if (binding.edEndDate.text.toString().isEmpty()) {
                    showToast("Please enter end date")
                } else {
                    if (areDatesValid()) {
                        viewModel.testScore(
                            binding.edStartdate.text.toString(),
                            binding.edEndDate.text.toString()
                        )
                    }
                }

            } else {
                showInternetToast()
            }

        }




        if (isNetworkConnected()) {
            viewModel.testScore("", "")
        } else {
            showInternetToast()
        }
        return binding.root
    }


    fun areDatesValid(): Boolean {
        var dateCompareOne = parseDate(binding.edStartdate.text.toString());
        var dateCompareTwo = parseDate(binding.edEndDate.text.toString());

//                if (dateCompareOne.before( dateCompareTwo )) {
//                    //your logic
//                }
        if (dateCompareTwo.before(dateCompareOne)) {
            //checkes whether the current time is between 14:49:00 and 20:11:13.
            showToast("End date should be greater than start date")

            return false
        }
        return true
    }

    fun parseDate(date: String): Date {

        var inputFormat = "yyyy-MM-dd";
        var inputParser = SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (e: ParseException) {
            return Date(0);
        }
    }

    fun showDatePickerDialog(editText: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in rotextbox
                var date = (year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth)
                editText.setText(date)

            },
            year,
            month,
            day
        )

        dpd.show()
    }


    fun setDummyDataOnChart(data: List<TestListing>, view: View) {
        x = ArrayList<Entry>()
        y = ArrayList<String>()
        //  binding.chart.setViewPortOffsets(0f, 20f, 0f, 0f)
        // binding.chart.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text

        // no description text
        binding.chart.getDescription().setEnabled(false)

        // enable touch gestures

        // enable touch gestures
        binding.chart.setTouchEnabled(true)
        binding.chart.setPinchZoom(false)


        // enable scaling and dra10gging
        // enable scaling and dragging
        binding.chart.setDragEnabled(true)
        binding.chart.setScaleEnabled(true)
        // binding.chart.getLegend().setWordWrapEnabled(true);


        val yAxisRight = binding.chart.axisRight
        yAxisRight.isEnabled = false
        // if disabled, scaling can be done on x- and y-axis separately

        // if disabled, scaling can be done on x- and y-axis separately
        // binding.chart.setPinchZoom(false)

        binding.chart.setDrawGridBackground(false)
        //  chart.setMaxHighlightDistance(300)

        val x: XAxis = binding.chart.getXAxis()

        x.setPosition(XAxis.XAxisPosition.BOTTOM)
        // x.valueFormatter = MyXAxisFormatter()
        x.setLabelCount(testnamearray.size);
        binding.chart.setScaleMinima(1.5f, 0.6f)

        //new code

        // axis range

        // axis range

        binding.chart.getXAxis().setValueFormatter(IndexAxisValueFormatter(testnamearray));

        //new code
        x.isEnabled = true

        if (data.size > 0) {
            //  for (i in 0 until data.size){
            setData(/*data.get(i).id, data.get(i).testPercentage.toFloat()*/data, x)
            //   }
            // set for whole data object (individual DataSets also possible)
        }


        val y: YAxis = binding.chart.getAxisLeft()
        y.typeface = ResourcesCompat.getFont(requireContext(), R.font.regular)
        // y.setLabelCount(6, false)
        y.textColor = Color.BLACK
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        y.setDrawGridLines(true)
        //  y.axisLineColor = Color.WHITE

        binding.chart.getAxisRight().setEnabled(false)


        // add data


        // binding.chart.getLegend().setEnabled(false)


        binding.chart.animateXY(2000, 2000)
        //  binding.chart.setExtraOffsets(10F, 20F, 50f, 10F);
        // don't forget to refresh the drawing

        // don't forget to refresh the drawing
        binding.chart.invalidate()

    }

    class MyXAxisFormatter : ValueFormatter() {


        private val days = arrayOf("Mo", "Tu", "Wed", "Th", "Fr", "Sa", "Su")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {

            Log.e("test aaray--", "--" + testnamearray)
            return testnamearray.getOrNull(value.toInt()) ?: value.toString()
        }
    }

    private fun setData(/*count: Int, range: Float*/data: List<TestListing>, xAxis: XAxis) {
        values.clear()



        for (i in 0 until data.size) {
            //  val `val` = (Math.random() * (range + 1)).toFloat() + 20
            values.add(
                Entry(
                    (i).toFloat(),
                    data.get(i).testPercentage.toInt().toFloat()
                )
            )


        }


        // values.add(Entry(0.0f, 0.0f))
        Log.e("values aaray--", "--" + Gson().toJson(values))
        val set1: LineDataSet
        if (binding.chart.getData() != null &&
            binding.chart.getData().getDataSetCount() > 0
        ) {
            set1 = binding.chart.getData().getDataSetByIndex(0) as LineDataSet
            set1.setValues(values)
            binding.chart.getData().notifyDataChanged()
            binding.chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "")
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER)
            //  set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)

            // set1.setDrawCircles(false)
            //     set1.setValueFormatter(MyXAxisFormatter())
            set1.lineWidth = 1.8f
            // set1.circleRadius = 2f
            set1.setCircleColor(Color.LTGRAY)
            // set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.LTGRAY
            set1.fillColor = Color.LTGRAY
            //  set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.setFillFormatter(object : IFillFormatter {
                override fun getFillLinePosition(
                    dataSet: ILineDataSet?,
                    dataProvider: LineDataProvider?
                ): Float {
                    return binding.chart.getAxisLeft().getAxisMinimum()
                }
            })


            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTypeface(ResourcesCompat.getFont(requireContext(), R.font.regular))
            data.setValueTextSize(9f)
            data.setDrawValues(true)

            // set data
            binding.chart.setData(data)
        }
    }
    /*  fun graphList(data: List<TestListing>, xl: XAxis) {
          val array = ArrayList<DummyChartData>()

          //    array.add(DummyChartData(0,""))
          for (i in 0 until data.size) {
              array.add(
                  DummyChartData(
                      data.get(i).testPercentage.toString(),
                      data.get(i).testName.toString()
                  )
              )
          }

          for (i in 0 until array.size) {

              val score = array.get(i).score
              val date = array.get(i).date

              x!!.add(Entry(score.toFloat(), i))
              y!!.add(date)
          }
          val set1 = LineDataSet(x, Token)
          //set1.setColors(ColorTemplate.COLORFUL_COLORS)
          val colors: ArrayList<Int> = ArrayList()
          var value: Float = set1.yVals.get(0).`val`
          if (data.size > 1) {
              for (i in 1 until set1.entryCount) {
                  if (set1.yVals.get(i).`val` < value) {
                      colors.add(resources.getColor(R.color.black))
                      value = set1.yVals.get(i).`val`
                  } else {
                      colors.add(resources.getColor(R.color.black))
                      value = set1.yVals.get(i).`val`
                  }
              }
          } else {
              for (i in 0 until set1.entryCount) {
                  if (set1.yVals.get(i).`val` < value) {
                      colors.add(resources.getColor(R.color.black))
                      value = set1.yVals.get(i).`val`
                  } else {
                      colors.add(resources.getColor(R.color.black))
                      value = set1.yVals.get(i).`val`
                  }
              }
          }

         set1.setColors(colors)
          if (data.size == 1) {
              set1.setDrawCircles(true)
              xl.mAxisMinimum = -0.5f
              xl.mAxisMaximum = 0.5f

          } else {
              set1.setDrawCircles(false)
  //            xl.mAxisMinimum = 0.0f
  //            xl.mAxisMaximum= (data.size-1).toFloat()
          }
          set1.setDrawValues(false)

          set1.lineWidth = 1.5f
          set1.circleRadius = 5f


          val data = LineData(y, set1)

          if (array.size == 1) {
              mChart!!.setExtraOffsets(0F, 0F, 50f, 10F);
          } else {
              mChart!!.setExtraOffsets(0F, 0F, 10f, 10F);
          }


          mChart!!.data = data
          mChart!!.invalidate()

      }*/
}