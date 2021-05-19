package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.TopicClicks
import snow.app.eduhub.ui.WorksheetListingScreen
import snow.app.eduhub.ui.network.responses.topicdetailsres.Worksheet
import snow.app.eduhub.ui.network.responses.worksheetlist.Data
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.AppUtils.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WorksheetListAdapter(
    var contxt: Context,
    var all_pdfs: List<Data>

) :
    RecyclerView.Adapter<WorksheetListAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_worksheet_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var diff_miliseconds: Long = 0
        var api_time: Long = 0
        var show_time: Long = 0
        val dataModel = all_pdfs.get(position)
        holder.tv_pdf_des.setText(dataModel.worksheetDes)
        holder.tv_pdfname.setText(dataModel.worksheetTitle)
      //  diff_miliseconds = getDifferenceBetweenDates(getCurrentDateTime(), dataModel.updatedDate)
      //  api_time = dataModel.worksheetTime * 60 * 60 * 1000



      //  show_time = api_time - diff_miliseconds

        holder.tv_created_date.setText("Uploaded on: "+convertDateToTime(dataModel.updatedDate))
        holder.teachername.setText("Uploaded By: "+dataModel.teacherName)
         holder.topicname.setText("Topic: "+dataModel.subjectName )
         holder.chaptername.setText("Chapter: "+dataModel.chapterName+ "(" + dataModel.subjectName + ")")

        Log.e(
            "time to show",
            "--   " + AppUtils.getworksheetTime(
                dataModel.updatedDate,
                dataModel.worksheetTime.toInt()
            )
        )
        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

        holder.tv_que.setOnClickListener {
            if ((contxt as WorksheetListingScreen).isNetworkConnected()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.worksheetPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        /*   holder.tv_hint.visibility = View.VISIBLE
           holder.tv_hint.setText(
               "Hint : Solution pdf will appear after " + convertDateToTime
                   ( getworksheetTime(dataModel.createdAt,dataModel.worksheet_time.toInt())) + " "
           )

   */









        if (isTimeCrossed(
                getworksheetTime(
                    dataModel.updatedDate,
                    dataModel.worksheetTime.toInt()
                )
            )
        ) {
            holder.tv_ans.visibility = View.VISIBLE
            holder.tv_hint.visibility = View.GONE
        } else {
            holder.tv_ans.visibility = View.GONE
            holder.tv_hint.visibility = View.VISIBLE
            holder.tv_hint.setText(
                "Hint : Solution pdf will appear after " + convertDateToTime
                    (getworksheetTime(dataModel.updatedDate, dataModel.worksheetTime.toInt())) + " "
            )

        }
/*
        if (dataModel.worksheet_time > 0) {
            if (hours >= dataModel.worksheet_time) {
                holder.tv_ans.visibility = View.VISIBLE
                holder.tv_hint.visibility = View.GONE
            } else {
                holder.tv_ans.visibility = View.GONE
                holder.tv_hint.visibility = View.VISIBLE
                holder.tv_hint.setText(
                    "Hint : Solution pdf will appear after " + getDiffInddmmyyBetweenDates(
                        getCurrentDateTime(),
                        dataModel.createdAt, dataModel.worksheet_time.toInt() / 60
                    ) + " "
                )


            }

        }*/

        //  Log.e("Hours", "--" + getDifferenceBetweenDates(getCurrentDateTime(), dataModel.createdAt))
        holder.tv_ans.setOnClickListener {
            if ((contxt as WorksheetListingScreen).isNetworkConnected()) {
                (contxt as WorksheetListingScreen).viewModel?.resprecentContinueTopic(
                    dataModel.chapterId.toString(),
                    dataModel.subjectId.toString(),
                    dataModel.topicId.toString(),"1"
                )
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.answerPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }


        }
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

    override fun getItemCount(): Int {
        return all_pdfs.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var tv_pdfname: TextView = view!!.findViewById(R.id.tv_pdfname)
        var tv_pdf_des: TextView = view!!.findViewById(R.id.pdf_des)
        var tv_created_date: TextView = view!!.findViewById(R.id.tv_created_date)
        var tv_que: TextView = view!!.findViewById(R.id.tv_que)
        var tv_ans: TextView = view!!.findViewById(R.id.tv_ans)
        var tv_hint: TextView = view!!.findViewById(R.id.tv_hint)
        var teachername: TextView = view!!.findViewById(R.id.teachername)
        var topicname: TextView = view!!.findViewById(R.id.topicname)
        var chaptername: TextView = view!!.findViewById(R.id.chaptername)

    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        // holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        //  holder.unbind()
    }

    init {
        data = ArrayList()
    }
}