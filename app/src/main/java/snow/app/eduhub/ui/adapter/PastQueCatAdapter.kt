package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.QuestionPaperPdfs
import snow.app.eduhub.R
import snow.app.eduhub.ui.TeacherListingScreen
import snow.app.eduhub.ui.TestListActivity
import snow.app.eduhub.ui.network.responses.fetchquestionpprcat.Data

import snow.app.eduhub.util.AppUtils

class PastQueCatAdapter(var contxt: Context, var list: List<Data>) :
    RecyclerView.Adapter<PastQueCatAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_que_ppr_cat, parent, false)
        /*  itemView.layoutParams = ViewGroup.LayoutParams(
              (parent.width * 0.5).toInt(),
              ViewGroup.LayoutParams.MATCH_PARENT
          )*/
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PastQueCatAdapter.MyViewHolder, position: Int) {
        var item = list.get(position)
        holder.subjectName.setText(item.categoryName)


        holder.rl_main.setOnClickListener {
            var intent = Intent(contxt, QuestionPaperPdfs::class.java)
            intent.putExtra("subject_id", item.subjectId.toString())
            intent.putExtra("past_question_category_id", item.id.toString())
            intent.putExtra("past_question_category_name", item.categoryName.toString())
            contxt.startActivity(intent)
        }

    }


    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        val subjectName: TextView = view!!.findViewById(R.id.tv_ppr_cat_name)
        val rl_main: RelativeLayout = view!!.findViewById(R.id.rl_main)
    }

}