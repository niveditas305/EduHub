package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.TeacherProfile
import snow.app.eduhub.ui.ChapterFurtherClick
import snow.app.eduhub.ui.TopicClicks
import snow.app.eduhub.ui.network.responses.topicdetails.TopicPdf


class ChapterDetailssAdapter(
    var contxt: Context,
    var data: List<TopicPdf>,
    var chapterFurtherClick: ChapterFurtherClick
) :
    RecyclerView.Adapter<ChapterDetailssAdapter.MyViewHolder>() {
    /*  var data: List<String> = ArrayList()
      fun updateData(data: List<String>) {
          this.data = data
          notifyDataSetChanged()
          Log.e("data", "data")
      }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chapter_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataModel = data.get(position)

        holder.pdf_name.setText(dataModel.pdfName)
        holder.tv_pdf_des.setText(dataModel.pdfDescription)
        holder.tv_rating.setText(dataModel.teacherRating)
        holder.tv_teachername.setText(dataModel.teacherFirstName + " " + dataModel.teacherLastName)

        holder.rv_parent.setOnClickListener {

            val intent = Intent(contxt, TopicClicks::class.java)
            intent.putExtra("teacherId", dataModel.teacherId.toString())
            intent.putExtra("subjectId", dataModel.subjectId.toString())
            intent.putExtra("chapterId", dataModel.chapterId.toString())
            intent.putExtra("topic_id", dataModel.id.toString())
            intent.putExtra("topic_name", "")
            contxt.startActivity(intent)
        }
        holder.tv_view_profile.setOnClickListener {

            val intent = Intent(contxt, TeacherProfile::class.java)
            intent.putExtra("teacher_id", dataModel.teacherId.toString())
            contxt.startActivity(intent)
        }

        /* holder.itemView.findViewById<RelativeLayout>(R.id.rv_parent).setOnClickListener {

             val intent = Intent(contxt, TopicClicks::class.java)
             intent.putExtra("teacher_id", dataModel.teacherId.toString())
             contxt.startActivity(intent)
          }*/


        /*   holder.iv_pdf.setOnClickListener {

               val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.pdfPath))
               contxt.startActivity(browserIntent)
           } */

        holder.ll_top.setOnClickListener {


            if (chapterFurtherClick.isNetworkConnected()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.pdfPath))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        var pdf_name: TextView = view!!.findViewById(R.id.pdf_name)
        var tv_pdf_des: TextView = view!!.findViewById(R.id.tv_pdf_des)
        var ll_top: RelativeLayout = view!!.findViewById(R.id.ll_top)
        var rv_parent: RelativeLayout = view!!.findViewById(R.id.rv_parent)
        var view_pdf: TextView = view!!.findViewById(R.id.view_pdf)
        var tv_teachername: TextView = view!!.findViewById(R.id.tv_teachername)
        var tv_rating: TextView = view!!.findViewById(R.id.tv_rating)
        var tv_view_profile: TextView = view!!.findViewById(R.id.tv_view_profile)
        var iv_pdf: ImageView = view!!.findViewById(R.id.iv_pdf)
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        // holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        //  holder.unbind()
    }

    fun isNetworkConnected(): Boolean {
        val cm =
            chapterFurtherClick.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
//    init {
//        data = ArrayList()
//    }
}