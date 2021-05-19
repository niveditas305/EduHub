package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.ChapterFurtherClick
import snow.app.eduhub.ui.ContinueDetails
import snow.app.eduhub.ui.network.responses.getChapters.Chapter
import snow.app.eduhub.ui.network.responses.getChapters.Topic


class ChaptersAdapter(var contxt: Context, var list: List<Chapter>,var continueDetails: ContinueDetails) :
    RecyclerView.Adapter<ChaptersAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()

    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chapters, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /* val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/
        val dataModel = list.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_down).setOnClickListener {
            if (holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility == View.VISIBLE) {
                holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility = View.GONE
            } else {
                holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility =
                    View.VISIBLE
            }


            holder.itemView.findViewById<ImageView>(R.id.iv_up).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.iv_down).visibility = View.GONE

        }
        holder.itemView.findViewById<ImageView>(R.id.iv_up).setOnClickListener {
            if (holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility == View.VISIBLE) {
                holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility = View.GONE
            } else {
                holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility =
                    View.VISIBLE
            }


            holder.itemView.findViewById<ImageView>(R.id.iv_up).visibility = View.GONE
            holder.itemView.findViewById<ImageView>(R.id.iv_down).visibility = View.VISIBLE

        }


        holder.itemView.findViewById<RelativeLayout>(R.id.rv_parent).setOnClickListener {

continueDetails.viewModel.
resprecentContinueTopic(dataModel.id.toString(),dataModel.subjectId.toString(),dataModel.teacherId.toString())
val intent=Intent(contxt,ChapterFurtherClick::class.java)
            intent.putExtra("teacherId",dataModel.teacherId.toString())
            intent.putExtra("subjectId",dataModel.subjectId.toString())
            intent.putExtra("chapterId",dataModel.id.toString())
            intent.putExtra("chaptername",dataModel.chapterName.toString())
            contxt.startActivity(intent)



        }

        holder.tv_chapter_name.setText(dataModel.chapterName)

        if (dataModel.topic.size==0){
            holder.tv_topic_count.setText("No topics found. ")

            holder.itemView.findViewById<ImageView>(R.id.iv_down).visibility=View.GONE
            holder.itemView.findViewById<ImageView>(R.id.iv_up).visibility=View.GONE
         }
       else if (dataModel.topic.size==1){



            holder.itemView.findViewById<ImageView>(R.id.iv_down).visibility=View.VISIBLE
             holder.tv_topic_count.setText(dataModel.topicCount.toString() +" topic")
        }else{

            holder.itemView.findViewById<ImageView>(R.id.iv_down).visibility=View.VISIBLE
             holder.tv_topic_count.setText(dataModel.topicCount.toString() +" topics")
        }


        if (dataModel.topic.size > 0) {
            setTopicAdapter(holder.rv_topics, dataModel.topic)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null


        var rv_topics: RecyclerView = view!!.findViewById(R.id.rv_topics)
        var tv_chapter_name: TextView = view!!.findViewById(R.id.tv_chapter_name)
        var tv_topic_count: TextView = view!!.findViewById(R.id.tv_topic_count)

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

    fun setTopicAdapter(rv_topics: RecyclerView, list: List<Topic>) {
        val linearLayoutManagertut = LinearLayoutManager(contxt)
        rv_topics.layoutManager = linearLayoutManagertut
        val topicsAdapter = TopicsAdapter(contxt, list)
        rv_topics.adapter = topicsAdapter
    }
}