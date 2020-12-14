package snow.app.eduhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.MainActivity
import snow.app.eduhub.R
import snow.app.eduhub.ui.TopicClicks
import snow.app.eduhub.ui.fragments.HomeFragment
import snow.app.eduhub.ui.network.responses.homedatares.TopTopic
import java.util.ArrayList

class HomeTopPickAdapter(var contxt: Context, var list: List<TopTopic>,var mainActivity: HomeFragment) :
    RecyclerView.Adapter<HomeTopPickAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

         holder.rl_parent.setOnClickListener {

             mainActivity.viewModel?.
             resprecentContinueTopic(list.get(position).chapterId.toString(),list.get(position).subjectId.toString(),list.get(position).teacherId.toString())

              var intent:Intent= Intent(contxt,TopicClicks::class.java)
             intent.putExtra("chapterId",list.get(position).chapterId.toString())
             intent.putExtra("teacherId",list.get(position).teacherId.toString())
             intent.putExtra("subjectId",list.get(position).subjectId.toString())
             intent.putExtra("topic_id",list.get(position).id.toString())
               contxt.startActivity(intent)
           }

        holder.chapter_name.setText(list.get(position).topicName)

        if (list.get(position).subjectName != null) {

            holder.tv_subjects.setText(list.get(position).subjectName)
        } else {
            holder.tv_subjects.visibility = View.GONE
        }

        holder.chapter_description.setText(list.get(position).topicDescription)
        // AppUtils.ImageWithGlide(holder.chapter_image, list.get(position).chapter_image)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null

        var chapter_name: TextView = view!!.findViewById(R.id.tv_chaptername)
        var chapter_description: TextView = view!!.findViewById(R.id.tv_chapterdes)
        var tv_subjects: TextView = view!!.findViewById(R.id.tv_subjects)
        var chapter_image: ImageView = view!!.findViewById(R.id.ll_top)
        var rl_parent: RelativeLayout = view!!.findViewById(R.id.rl_parent)


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