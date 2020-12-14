package snow.app.eduhub.ui.adapter

import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.network.responses.searchres.Topic


class SearchTopicsAdapter(
    var contxt: Context,
    var list: List<Topic>

) :
    RecyclerView.Adapter<SearchTopicsAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    var serialno: Int = 0
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_topics, parent, false)
        /* itemView.layoutParams = ViewGroup.LayoutParams(
             (parent.width * 0.5).toInt(),
             ViewGroup.LayoutParams.MATCH_PARENT
         )*/
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataModel = list.get(position)
        serialno = position + 1
        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

        /* holder.itemView.findViewById<LinearLayout>(R.id.ll_parent).setOnClickListener {
               contxt.startActivity(Intent(contxt, PostedJobView::class.java))
           }*/

        holder.tv_topic_name.setText("" + serialno + ". " + dataModel.topicName)


    }

    override fun getItemCount(): Int {


        return list.size

    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null

        var tv_topic_name: TextView = view!!.findViewById(R.id.tv_topic_name)


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