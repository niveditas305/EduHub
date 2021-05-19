package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.ContinueDetails
import snow.app.eduhub.util.AppUtils

class SubjectListByIdAdapter(var contxt: Context, var list: List<snow.app.eduhub.ui.network.responses.getsubjectlistbyid.Data>,var teacher_id:String) :
    RecyclerView.Adapter<SubjectListByIdAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_subjects, parent, false)
      /*  itemView.layoutParams = ViewGroup.LayoutParams(
            (parent.width * 0.5).toInt(),
            ViewGroup.LayoutParams.MATCH_PARENT
        )*/
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SubjectListByIdAdapter.MyViewHolder, position: Int) {
        var item = list.get(position)
        holder.subjectName.setText(item.subjectName)
        AppUtils.ImageWithGlide(holder.image, item.subjectImage)
        if ((position % 2) == 0) {
            holder.container.setBackgroundColor(contxt.resources.getColor(R.color.red))
        } else {
            holder.container.setBackgroundColor(contxt.resources.getColor(R.color.colorAccent))
        }

        holder.container.setOnClickListener {
            val intent = Intent(contxt, ContinueDetails::class.java)
            intent.putExtra("teacherId", teacher_id)
            intent.putExtra("subjectId", item.id.toString())
            intent.putExtra("subjectname", item.subjectName)
            contxt.startActivity(intent)


        }

    }


    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val subjectName: TextView = view!!.findViewById(R.id.subject_name)
        val image: ImageView = view!!.findViewById(R.id.image)
        val container: LinearLayout = view!!.findViewById(R.id.container)
    }

}