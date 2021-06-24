package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.MainActivity
import snow.app.eduhub.R
import snow.app.eduhub.ui.SelectGrade
import snow.app.eduhub.ui.TeacherListingScreen
import snow.app.eduhub.ui.network.responses.homedatares.Subject
import snow.app.eduhub.util.AppUtils

class SubjectsAdapter(var contxt: Context, var list: List<Subject>) :
    RecyclerView.Adapter<SubjectsAdapter.MyViewHolder>() {
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

    override fun onBindViewHolder(holder: SubjectsAdapter.MyViewHolder, position: Int) {
        var item = list.get(position)
        holder.subjectName.setText(item.subjectName)
     //   AppUtils.ImageWithGlide(holder.image, item.subjectImage)
        AppUtils.ImageWithGlide(holder.ivSub, item.subjectImage)
     /*   if ((position % 2) == 0) {
            holder.container.setBackgroundColor(contxt.resources.getColor(R.color.red))
        } else {
            holder.container.setBackgroundColor(contxt.resources.getColor(R.color.colorAccent))
        }*/

        holder.container.setOnClickListener {
        /*    var intent = Intent(contxt, TeacherListingScreen::class.java)
            intent.putExtra("subjectname", item.subjectName.toString())
            intent.putExtra("subjectid", item.id.toString())
            contxt.startActivity(intent)*/
            var intent = Intent(contxt, SelectGrade::class.java)
             intent.putExtra("subject_id", item.id.toString())
            contxt.startActivity(intent)

         /*   (contxt as MainActivity).showToast("sub id--"+item.id)
            var intent = Intent(contxt, SelectGrade::class.java).putExtra("subject_id "
                ,list.get(position).id.toString())
              contxt.startActivity(intent)*/
        }

    }


    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        val subjectName: TextView = view!!.findViewById(R.id.subject_name)
/*        val image: ImageView = view!!.findViewById(R.id.image)*/
        val ivSub: ImageView = view!!.findViewById(R.id.ivSub)
        val container: RelativeLayout = view!!.findViewById(R.id.container)
    }

}