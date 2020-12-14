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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.TeacherProfile
import snow.app.eduhub.ui.LearnActivity
import snow.app.eduhub.ui.network.responses.testsummaryres.Data
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.LikeDislikeListener


class TestSummrayAdapter(
    var contxt: Context,
    var list: List<Data>/*, var subjectId: String*/

) :
    RecyclerView.Adapter<TestSummrayAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_test_summary, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tv_question_no.setText(list.get(position).questionNumber.toString() + "")
        if (list.get(position).attemptStatus == 1 && list.get(position).resultStatus == 1) {
            holder.tv_question_no.setBackgroundResource(R.drawable.bg_correct_ans)
            holder.tv_question_no.setTextColor(ContextCompat.getColor(contxt, R.color.white))

        } else if (list.get(position).attemptStatus == 1 && list.get(position).resultStatus == 0) {
            holder.tv_question_no.setBackgroundResource(R.drawable.wrong_ans_bg)
            holder.tv_question_no.setTextColor(ContextCompat.getColor(contxt, R.color.white))
        } else {
            holder.tv_question_no.setBackgroundResource(R.drawable.test_summary_stroke)
            holder.tv_question_no.setTextColor(ContextCompat.getColor(contxt, R.color.black))
        }

    }

    override fun getItemCount(): Int {
        return  /* data.size*/ list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null

        //   var container: LinearLayout = view!!.findViewById(R.id.container)


        var tv_question_no: TextView = view!!.findViewById(R.id.tv_question_no)


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