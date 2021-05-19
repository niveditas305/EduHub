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
import snow.app.eduhub.ui.TestSeriesActivity
import snow.app.eduhub.ui.network.responses.getChapters.Chapter
import snow.app.eduhub.ui.network.responses.getChapters.Topic
import snow.app.eduhub.ui.network.responses.testlistres.CurrentWeekTest
import snow.app.eduhub.ui.network.responses.testlistres.OldWeekTest
import snow.app.eduhub.util.AppUtils


class OlderTestLIstAdapter(var contxt: Context, var list: List<OldWeekTest>) :
    RecyclerView.Adapter<OlderTestLIstAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()

    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_test_science, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /* val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/
        val dataModel = list.get(position)



        if (dataModel.test_attempt_status == 1) {
            holder.tv_test_status.visibility = View.VISIBLE
            holder.tv_test_status.setText("Last scored: " + dataModel.last_attempt_score + "%")
        } else {
            holder.tv_test_status.visibility = View.GONE
        }

        holder.itemView.findViewById<LinearLayout>(R.id.rv_parent).setOnClickListener {
            val intent = Intent(contxt, TestSeriesActivity::class.java)
            intent.putExtra("teacherId", dataModel.teacherId.toString())
            intent.putExtra("testId", dataModel.id.toString())
            intent.putExtra("testname", dataModel.setName.toString())
            contxt.startActivity(intent)
        }

        holder.tv_test_name.setText(dataModel.setName)
        holder.tv_test_des.setText(dataModel.setDescription)
        holder.tv_passing.setText("Passing marks: "+dataModel.passing_marks +"%")
        holder.tv_test_date.setText("Uploaded on : " + AppUtils.getFormatDate(dataModel.createdAt))




        if (dataModel.last_attempt_score.toDouble() > dataModel.passing_marks.toDouble()) {
            holder.tv_note.visibility = View.GONE

        } else {
            holder.tv_note.visibility = View.VISIBLE

            holder.tv_note.setText("You need improvement in " + dataModel.topic_name + "(" + dataModel.chapter_name + ")")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var tv_test_name: TextView = view!!.findViewById(R.id.tv_test_name)
        var tv_test_des: TextView = view!!.findViewById(R.id.tv_test_des)
        var tv_test_date: TextView = view!!.findViewById(R.id.tv_test_date)
        var tv_test_status: TextView = view!!.findViewById(R.id.tv_test_status)
        var tv_note: TextView = view!!.findViewById(R.id.tv_note)
        var tv_passing: TextView = view!!.findViewById(R.id.tv_passing)
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