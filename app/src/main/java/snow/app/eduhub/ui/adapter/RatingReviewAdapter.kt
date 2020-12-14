package snow.app.eduhub.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.network.responses.teachersprofile.Reviews
import snow.app.eduhub.util.AppUtils


class RatingReviewAdapter(var contxt: Context, var list: List<Reviews>) :
    RecyclerView.Adapter<RatingReviewAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_review, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val dataModel = list.get(position)

        holder.tv_name.setText(dataModel.firstName + " " + dataModel.lastName)
        holder.tv_review.setText(dataModel.reviewContent)

        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

        AppUtils.roundImageWithGlide(holder.iv_user_image, dataModel.studentProfile)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: Row? = null
        var iv_user_image: ImageView = view!!.findViewById(R.id.iv_user_image);
        var tv_name: TextView = view!!.findViewById(R.id.tv_name);
        var tv_review: TextView = view!!.findViewById(R.id.tv_review);
        //  lateinit var textView: TextView
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