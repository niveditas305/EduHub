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
import snow.app.eduhub.ui.TopicClicks
import snow.app.eduhub.ui.network.responses.getChapters.Chapter
import snow.app.eduhub.ui.network.responses.getChapters.Topic
import snow.app.eduhub.ui.network.responses.topicdetails.Data
import snow.app.eduhub.ui.network.responses.topicdetails.TopicPdf


class TopicDetailsAdapter(var contxt: Context, var list: List<Data>,var chapterFurtherClick: ChapterFurtherClick) :
    RecyclerView.Adapter<TopicDetailsAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()

    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_topic_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /* val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/
        val dataModel = list.get(position)

/*
        holder.itemView.findViewById<RelativeLayout>(R.id.rv_parent).setOnClickListener {


val intent=Intent(contxt,ChapterFurtherClick::class.java)
            intent.putExtra("teacherId",dataModel.teacherId.toString())
            intent.putExtra("subjectId",dataModel.subjectId.toString())
            intent.putExtra("chapterId",dataModel.id.toString())
            contxt.startActivity(intent)



        }*/

        holder.tv_topic_name.setText(dataModel.topicName)

        if (dataModel.topicPdf.size==0){
            holder.no_record_found.visibility=View.VISIBLE
        }else{
            holder.no_record_found.visibility=View.GONE
        }



        if (dataModel.topicPdf.size > 0) {
            setTopicAdapter(holder.rv_pdfs, dataModel.topicPdf,chapterFurtherClick)

        }

        holder.tv_topic_name.setOnClickListener {


            if (chapterFurtherClick.isNetworkConnected()){
                chapterFurtherClick.viewModel.resprecentContinueTopic(
                    dataModel.chapterId.toString(),
                    dataModel.subjectId.toString(),
                    dataModel.id.toString()
                )
            }



            val intent = Intent(contxt, TopicClicks::class.java)
            intent.putExtra("teacherId", dataModel.teacherId.toString())
            intent.putExtra("subjectId", dataModel.subjectId.toString())
            intent.putExtra("chapterId", dataModel.chapterId.toString())
            intent.putExtra("topic_id", dataModel.id.toString())
            intent.putExtra("topic_name", dataModel.topicName.toString())
            contxt.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null


        var rv_pdfs: RecyclerView = view!!.findViewById(R.id.rv_pdfs)
         var tv_topic_name: TextView = view!!.findViewById(R.id.tv_topic_name)
         var no_record_found: TextView = view!!.findViewById(R.id.no_record_found)

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

    fun setTopicAdapter(rv_pdfs: RecyclerView, list: List<TopicPdf>,chapterFurtherClick:ChapterFurtherClick) {
        val linearLayoutManagertut = LinearLayoutManager(contxt, LinearLayoutManager.HORIZONTAL, false)
        rv_pdfs.layoutManager = linearLayoutManagertut
        val topicsAdapter = ChapterDetailssAdapter(contxt, list,chapterFurtherClick)
        rv_pdfs.adapter = topicsAdapter
    }
}