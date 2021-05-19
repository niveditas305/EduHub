package com.snow.beautyasap.ui.home.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import snow.app.eduhub.MainActivity
import snow.app.eduhub.QuestionPaperPdfs

import snow.app.eduhub.R
import snow.app.eduhub.ui.ChapterFurtherClick
import snow.app.eduhub.ui.ContinueDetails
import snow.app.eduhub.ui.TestListActivity
import snow.app.eduhub.ui.TopicClicks
import snow.app.eduhub.ui.network.responses.fetchnotficationres.Data


class NotificationListAdapter(
    var context: Activity,
    var arrayList: List<Data>
) :
    RecyclerView.Adapter<NotificationListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_notifications, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
/*

        1(new chapter add), 2 (new worksheet add), 3 (new topic add), 4 (new topic pdf),
        5(new topic video), 6 (new test add), 7 (new question paper), 8 (new chat message)

*/
        holder.cardview_parent.setOnClickListener {
            if (arrayList.get(position).notificationType == 1) { //1= new chapter add

                val intent: Intent = Intent(context, ContinueDetails::class.java)
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("subjectname", arrayList.get(position).subjectName.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 2) {   //3= new worksheet add
                val intent: Intent = Intent(context, TopicClicks::class.java)
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("topic_id", arrayList.get(position).topicId.toString())
                intent.putExtra("topic_name", arrayList.get(position).topicName.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 3) {   //1= new topic add
                val intent: Intent = Intent(context, ChapterFurtherClick::class.java)
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("chaptername", arrayList.get(position).chapterName.toString())
                context.startActivity(intent)


            } else if (arrayList.get(position).notificationType == 4) {//4= new topic pdf
                val intent: Intent = Intent(context, ChapterFurtherClick::class.java)
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("chaptername", arrayList.get(position).chapterName.toString())
                context.startActivity(intent)


            } else if (arrayList.get(position).notificationType == 5) {//1= new topic video

                val intent: Intent = Intent(context, TopicClicks::class.java)
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("topic_id", arrayList.get(position).topicId.toString())
                intent.putExtra("topic_name", arrayList.get(position).topicName.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 6) {//1= new test add

                val intent: Intent = Intent(context, TestListActivity::class.java)
                intent.putExtra("subjectname", arrayList.get(position).subjectName.toString())
                intent.putExtra("subjectid", arrayList.get(position).subjectId.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 7) {//1= new question paper

                val intent: Intent = Intent(context, QuestionPaperPdfs::class.java)
                intent.putExtra("past_question_category_id", arrayList.get(position).pastQuestionCategoryId.toString())
                intent.putExtra("past_question_category_name", arrayList.get(position).pastQuestionCategoryName.toString())
                intent.putExtra("subject_id", arrayList.get(position).subjectId.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 8) {//1= chat

                val intent: Intent = Intent(context, MainActivity::class.java)
                intent.putExtra("chat", "from")
                context.startActivity(intent)

            } else {
                val intent: Intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }
        holder.rl_root.setOnClickListener {
            if (arrayList.get(position).notificationType == 1) { //1= new chapter add

                val intent: Intent = Intent(context, ContinueDetails::class.java)
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("subjectname", arrayList.get(position).subjectName.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 2) {   //3= new worksheet add
                val intent: Intent = Intent(context, TopicClicks::class.java)
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("topic_id", "")
                intent.putExtra("topic_name", arrayList.get(position).topicName.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 3) {   //1= new topic add
                val intent: Intent = Intent(context, ChapterFurtherClick::class.java)
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("chaptername", arrayList.get(position).chapterName.toString())
                context.startActivity(intent)


            } else if (arrayList.get(position).notificationType == 4) {//4= new topic pdf
                val intent: Intent = Intent(context, ChapterFurtherClick::class.java)
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("chaptername", arrayList.get(position).chapterName.toString())
                context.startActivity(intent)


            } else if (arrayList.get(position).notificationType == 5) {//1= new topic video

                val intent: Intent = Intent(context, TopicClicks::class.java)
                intent.putExtra("teacherId", arrayList.get(position).teacherId.toString())
                intent.putExtra("chapterId", arrayList.get(position).chapterId.toString())
                intent.putExtra("subjectId", arrayList.get(position).subjectId.toString())
                intent.putExtra("topic_id", arrayList.get(position).topicId.toString())
                intent.putExtra("topic_name", arrayList.get(position).topicName.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 6) {//1= new test add

                val intent: Intent = Intent(context, TestListActivity::class.java)
                intent.putExtra("subjectname", arrayList.get(position).subjectName.toString())
                intent.putExtra("subjectid", arrayList.get(position).subjectId.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 7) {//1= new question paper

                val intent: Intent = Intent(context, QuestionPaperPdfs::class.java)
                intent.putExtra("past_question_category_id", arrayList.get(position).pastQuestionCategoryId.toString())
                intent.putExtra("past_question_category_name", arrayList.get(position).pastQuestionCategoryName.toString())
                intent.putExtra("subject_id", arrayList.get(position).subjectId.toString())
                context.startActivity(intent)

            } else if (arrayList.get(position).notificationType == 8) {//1= chat

                val intent: Intent = Intent(context, MainActivity::class.java)
                intent.putExtra("chat", "from")
                context.startActivity(intent)

            } else {
                val intent: Intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }






        holder.tv_notification_text.text = arrayList.get(position).message
        holder.tv_time.text = arrayList.get(position).formatedDateTime


        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.user)
            .error(R.drawable.user)



        Glide.with(context).load(arrayList.get(position).photo).apply(options)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        var image: ImageView = view!!.findViewById(R.id.img_left_);
        var cardview_parent: LinearLayout = view!!.findViewById(R.id.cardview_parent);
        var rl_root: RelativeLayout = view!!.findViewById(R.id.rl_root);
        var tv_notification_text: TextView = view!!.findViewById(R.id.tv_notification_text);
        var tv_time: TextView = view!!.findViewById(R.id.tv_time);
    }


}