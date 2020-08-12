package com.example.eduhub.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eduhub.R
import com.example.eduhub.ui.TopicClicks


class ChapterDetailssAdapter (var contxt: Context): RecyclerView.Adapter<ChapterDetailssAdapter.MyViewHolder>() {
  /*  var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_chapter_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
   /*     val dataModel = data[position]
        holder.setViewModel(HomeRowVm())
        Log.e("onBindViewHolder", "data")*/

  holder.itemView.findViewById<RelativeLayout>(R.id.rv_parent).setOnClickListener {
//        if (holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility==View.VISIBLE){
//            holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility=View.GONE
//        }else{
//            holder.itemView.findViewById<LinearLayout>(R.id.ll_subtopic).visibility=View.VISIBLE
//        }
//


      contxt.startActivity(Intent(contxt,TopicClicks::class.java))
       }
    }

    override fun getItemCount(): Int {
        return  /* data.size*/ 6
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
       // var binding: RowHomebind? = null

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

//    init {
//        data = ArrayList()
//    }
}