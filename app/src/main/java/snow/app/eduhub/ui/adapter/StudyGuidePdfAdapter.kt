package snow.app.eduhub.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import snow.app.eduhub.R
import snow.app.eduhub.ui.StudyGuideActivity
import snow.app.eduhub.ui.network.responses.getstudyguild.Data
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.AppUtils.*
import snow.app.eduhub.util.PdfClickInterface
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StudyGuidePdfAdapter(
    var contxt: Context,
    var all_pdfs: ArrayList<Data>,
    val pdfListenr: PdfClickInterface
) :
    RecyclerView.Adapter<StudyGuidePdfAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_pdf_study_guide, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var diff_miliseconds: Long = 0
        var api_time: Long = 0
        var show_time: Long = 0
        val dataModel = all_pdfs.get(position)
        holder.tv_pdf_des.setText(dataModel.videoDescription)
        holder.tv_pdfname.setText(dataModel.videoName)
        /*  diff_miliseconds = getDifferenceBetweenDates(getCurrentDateTime(), dataModel.updatedAt)
          api_time = dataModel.worksheet_time * 60 * 60 * 1000



          show_time = api_time - diff_miliseconds*/

        holder.tv_created_date.setText("Uploaded on: " + pdftime(dataModel.updatedAt))


        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/


        AppUtils.ImageWithGlide(holder.iv, dataModel.pdfIcon)
       /* holder.tv_que.setOnClickListener {
            if ((contxt as MainActivity).isNetworkConnected()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.studyPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }

        }*/


         holder.rv_parent.setOnClickListener {
            if ((contxt as StudyGuideActivity).isNetworkConnected()) {

                pdfListenr.onSubmitClick(dataModel.studyPdf, "pdf")
               /* val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.studyPdf))
                contxt.startActivity(browserIntent)*/

           /*     val playerMetaData = PlayerMetaData(contxt)
                playerMetaData.setServerId("rikshot")
                playerMetaData.commit()

                val ordinalMetaData = MediationMetaData(contxt)
                ordinalMetaData.setOrdinal(HomeFragment.ordinal++)
                ordinalMetaData.commit()*/


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


/*
        if (isTimeCrossed(
                getworksheetTime(
                    dataModel.updated_date,
                    dataModel.worksheet_time.toInt()
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
                    (getworksheetTime(dataModel.updated_date, dataModel.worksheet_time.toInt())) + " "
            )

        }*/
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
        /*   holder.tv_ans.setOnClickListener {
               if (topicClicks.isNetworkConnected()) {
                   topicClicks.viewModel.resprecentContinueTopic(
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


           }*/
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
        var iv: RoundedImageView = view!!.findViewById(R.id.iv)
        var rv_parent: RelativeLayout = view!!.findViewById(R.id.rv_parent)

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