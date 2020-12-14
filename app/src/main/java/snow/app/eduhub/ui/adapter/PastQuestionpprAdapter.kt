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
import snow.app.eduhub.QuestionPaperPdfs
import snow.app.eduhub.R
 import snow.app.eduhub.ui.network.responses.pastquestions.Data
import snow.app.eduhub.ui.network.responses.topicdetailsres.Worksheet


class PastQuestionpprAdapter(
    var contxt: Context,
    var all_pdfs: List<Data>,
    var topicClicks: QuestionPaperPdfs
) :
    RecyclerView.Adapter<PastQuestionpprAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_past_questionppr, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataModel = all_pdfs.get(position)

        holder.tv_pdf_des.setText(dataModel.questionDescription)
        holder.tv_pdfname.setText(dataModel.questionName)

        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

        holder.tv_ans.setOnClickListener {


            if (topicClicks.isNetworkConnected()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.questionPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }


        }


        holder.tv_class.setText("Class: "+dataModel.class_name +"th")
        holder.tv_date.setText("Uploaded on: " + dataModel.date)
        holder.tv_subject.setText("Subject: " + dataModel.subjectName)
        /*   holder.tv_ans.setOnClickListener {


               if (topicClicks.isNetworkConnected()) {
                   val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.answerPdf))
                   contxt.startActivity(browserIntent)
               } else {
                   Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                       .show()
               }


           }*/
    }

    override fun getItemCount(): Int {
        return all_pdfs.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var tv_pdfname: TextView = view!!.findViewById(R.id.tv_pdfname)
        var tv_pdf_des: TextView = view!!.findViewById(R.id.pdf_des)
        var tv_ans: TextView = view!!.findViewById(R.id.tv_ans)
        var tv_class: TextView = view!!.findViewById(R.id.tv_class)
        var tv_subject: TextView = view!!.findViewById(R.id.tv_subject)
        var tv_date: TextView = view!!.findViewById(R.id.tv_date)

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