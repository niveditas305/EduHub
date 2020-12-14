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
import snow.app.eduhub.ui.network.responses.getChapters.Chapter
import snow.app.eduhub.ui.network.responses.getChapters.Topic
import snow.app.eduhub.ui.network.responses.scoreres.ScoreListing


class SubScoreListingAdapter(var contxt: Context, var list: List<ScoreListing>) :
    RecyclerView.Adapter<SubScoreListingAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()

    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_subject_score, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /* val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/
        val dataModel = list.get(position)





        holder.tv_sub.setText(dataModel.subjectName)
        holder.tv_per.setText(""+dataModel.subjectWisePercentage +"%")

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null


        var tv_sub: TextView = view!!.findViewById(R.id.tv_sub)
        var tv_per: TextView = view!!.findViewById(R.id.tv_per)


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