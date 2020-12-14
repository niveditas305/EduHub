package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.TeacherProfile
import snow.app.eduhub.ui.LearnActivity
import snow.app.eduhub.ui.network.responses.fetchfavteachers.Data
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.LikeDislikeListener


class FavTeachersAdapter(
    var contxt: Context,
    var list: List<Data>/*, var subjectId: String*/,
    var listener: LikeDislikeListener
) :
    RecyclerView.Adapter<FavTeachersAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_row_teachers, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/
        /*  holder.itemView.findViewById<LinearLayout>(R.id.ll_parent).setOnClickListener {
             contxt.startActivity(Intent(contxt, TutorDetailsScreen::class.java))
         }
         holder.itemView.findViewById<TextView>(R.id.tv_rating).setOnClickListener {
             contxt.startActivity(Intent(contxt, TeacherReviewScreen::class.java))
         }*/

        /*   holder.container.setOnClickListener {
               var intent = Intent(contxt, ContinueDetails::class.java)
               intent.putExtra("teacherId", item.id)
               //    intent.putExtra("subjectId", item)
               contxt.startActivity(intent)
           }*/
        holder.teacher_name.setText(list.get(position).teacherFirstName + " " + list.get(position).teacherLastName)
        holder.tv_exp.setText(list.get(position).teacherExperence.toString() + " years Experience")

        holder.tv_subject_name.setText(list.get(position).teacherSubject)
        holder.iv_fav.visibility = View.VISIBLE
        holder.iv_unfav.visibility = View.GONE
        /*if (list.get(position).teacherFollow == 1) {
            holder.iv_fav.visibility = View.VISIBLE
            holder.iv_unfav.visibility = View.GONE
        } else {
            holder.iv_unfav.visibility = View.VISIBLE
            holder.iv_fav.visibility = View.GONE
        }
*/
        holder.iv_fav.setOnClickListener {
          /*  holder.iv_unfav.visibility = View.VISIBLE
            holder.iv_fav.visibility = View.GONE*/
            listener.likeClick(list.get(position).id.toString(),"2",holder.iv_fav,holder.iv_unfav)
        }

       /* holder.iv_unfav.setOnClickListener {
            holder.iv_unfav.visibility = View.GONE
            holder.iv_fav.visibility = View.VISIBLE
        }*/
        if (list.get(position).teacherRating != null) {
            holder.ratingBar.rating = list.get(position).teacherRating.toFloat()

            holder.tv_rating.setText("("+
                (String.format("%.1f", list.get(position).teacherRating.toFloat()).toString() +")")
            )

        } else {

            holder.tv_rating.setText(
                ("(0.0)")
            )
        }


        holder.iv_fav.setOnClickListener {
            listener.likeClick(
                list.get(position).toTeacher.toString(),
                "2",
                holder.iv_fav,
                holder.iv_unfav
            )

            //holder.iv_unfav.visibility = View.VISIBLE
            holder.iv_fav.visibility = View.GONE
        }


        AppUtils.roundImageWithGlide(holder.teacher_image, list.get(position).teacherImage)

        holder.tv_view.setOnClickListener {

            val intent = Intent(contxt, TeacherProfile::class.java)
            intent.putExtra("teacher_id", list.get(position).toTeacher.toString())
            contxt.startActivity(intent)
        }


        holder.parent_ll.setOnClickListener {

            val intent = Intent(contxt, LearnActivity::class.java)
            intent.putExtra("teacher_id", list.get(position).toTeacher.toString())
            contxt.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return  /* data.size*/ list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null

         //   var container: LinearLayout = view!!.findViewById(R.id.container)


        var teacher_name: TextView = view!!.findViewById(R.id.tv_teacher_name)
        var parent_ll: LinearLayout = view!!.findViewById(R.id.parent_ll)
        var tv_subject_name: TextView = view!!.findViewById(R.id.tv_subject_name)
        var tv_exp: TextView = view!!.findViewById(R.id.tv_exp)
        var tv_rating: TextView = view!!.findViewById(R.id.tv_rating)

        var ratingBar: com.fuzzproductions.ratingbar.RatingBar =
            view!!.findViewById(R.id.rating_bar)
        var teacher_image: ImageView = view!!.findViewById(R.id.iv_teacher)
        var iv_unfav: ImageView = view!!.findViewById(R.id.iv_unfav)
        var iv_fav: ImageView = view!!.findViewById(R.id.iv_fav)
        var tv_view: TextView = view!!.findViewById(R.id.tv_view)


//        fun bind() {
//            if (binding == null) {
//                binding = DataBindingUtil.bind(itemView)
//            }
//        }
//
//        fun unbind() {
//            if (binding != null) {
//                binding!!.unbind() // Don't forget to unbind
//            }
//        }
//
//        fun setViewModel(viewModel: HomeRowVm?) {
//            if (binding != null) {
//                binding!!.viewModel = viewModel
//                Log.e("setViewModel", "setViewModel")
//            }
//        }
//
//        init {
//            bind()
//        }
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