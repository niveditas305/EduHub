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
import snow.app.eduhub.ui.network.responses.topicdetailsres.Worksheet
import snow.app.eduhub.util.AppUtils.*


class WorksheetAdapter(
    var contxt: Context,
    var all_pdfs: List<Worksheet>,
    var topicClicks: TopicClicks
) :
    RecyclerView.Adapter<WorksheetAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_worksheet, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var hours: Long = 0
        val dataModel = all_pdfs.get(position)
        holder.tv_pdf_des.setText(dataModel.worksheetDes)
        holder.tv_pdfname.setText(dataModel.worksheetTitle)
        hours = getDifferenceBetweenDates(getCurrentDateTime(), dataModel.createdAt)

        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

        holder.tv_que.setOnClickListener {
            if (topicClicks.isNetworkConnected()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.worksheetPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        if (hours >= 24L) {
            holder.tv_ans.visibility = View.VISIBLE
            holder.tv_hint.visibility=View.GONE
        } else {
            holder.tv_ans.visibility = View.GONE
            holder.tv_hint.visibility=View.VISIBLE
            holder.tv_hint.setText(
                "Hint : Solution pdf will appear after " + getDiffInddmmyyBetweenDates(
                    getCurrentDateTime(),
                    dataModel.createdAt
                ) + " hours"
            )


        }



        Log.e("Hours", "--" + getDifferenceBetweenDates(getCurrentDateTime(), dataModel.createdAt))
        holder.tv_ans.setOnClickListener {
            if (topicClicks.isNetworkConnected()) {
                topicClicks.viewModel.resprecentContinueTopic(
                    dataModel.chapterId.toString(),
                    dataModel.subjectId.toString(),
                    dataModel.topicId.toString()
                )
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.answerPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }

    override fun getItemCount(): Int {
        return all_pdfs.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var tv_pdfname: TextView = view!!.findViewById(R.id.tv_pdfname)
        var tv_pdf_des: TextView = view!!.findViewById(R.id.pdf_des)
        var tv_que: TextView = view!!.findViewById(R.id.tv_que)
        var tv_ans: TextView = view!!.findViewById(R.id.tv_ans)
        var tv_hint: TextView = view!!.findViewById(R.id.tv_hint)

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