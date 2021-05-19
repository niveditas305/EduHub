package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.TeacherProfile
import snow.app.eduhub.ui.ContinueDetails
import snow.app.eduhub.ui.LessonListingScreen
import snow.app.eduhub.ui.TeacherReviewScreen
import snow.app.eduhub.ui.TutorDetailsScreen
import snow.app.eduhub.ui.network.responses.lessonlistres.Data
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.LikeDislikeListener


class LessonsAdapter(var contxt: Context, var list: List<Data>, var subjectid: String) :
    RecyclerView.Adapter<LessonsAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_lessons, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.teacher_name.setText("Uploaded by: " + list.get(position).teacherName)
        holder.tv_chapter.setText(
            "Chapter name: " + list.get(position).chapterName + "(" + list.get(
                position
            ).subjectName + ")"
        )
        holder.tv_uploaded_on.setText("Uploaded on: " + AppUtils.getFormatDate(list.get(position).updatedAt))

        holder.tv_lesson_name.setText(list.get(position).lessonName)
        holder.lesson_des.setText(list.get(position).lessonDescription)

        holder.tv_pdf.setOnClickListener {
            if ((contxt as LessonListingScreen).isNetworkConnected()) {


                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).lessonPdf))
                contxt.startActivity(browserIntent)
            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }


        }
/*

        if (list.get(position).teacherRating != null) {
            holder.ratingBar.rating = list.get(position).teacherRating.toFloat()
            holder.tv_rating.setText(
                (String.format(
                    "%.1f",
                    list.get(position).teacherRating.toFloat()
                ).toString())
            )
        } else {
            holder.tv_rating.setText(("(0.0)"))
        }
*/


    }

    override fun getItemCount(): Int {
        return  /* data.size*/ list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {


        var tv_uploaded_on: TextView = view!!.findViewById(R.id.tv_uploaded_on)
        var tv_chapter: TextView = view!!.findViewById(R.id.tv_chapter)
        var teacher_name: TextView = view!!.findViewById(R.id.teacher_name)
        var lesson_des: TextView = view!!.findViewById(R.id.lesson_des)
        var tv_lesson_name: TextView = view!!.findViewById(R.id.tv_lesson_name)
        var tv_pdf: TextView = view!!.findViewById(R.id.tv_pdf)

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