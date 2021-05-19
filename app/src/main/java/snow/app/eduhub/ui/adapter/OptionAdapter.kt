package snow.app.eduhub.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import snow.app.eduhub.R
import snow.app.eduhub.ui.network.responses.testquestionsres.Question
import snow.app.eduhub.util.*


class OptionAdapter(
    var contxt: Context,
    var list: ArrayList<OptionModel>,
    var getcorrectoption: GetIdfromAdapter,
    var que_id: String,
    var sub_id: String,
    var submitInterface: SubmitInterface,
    var getIds: GetIds, var quelist: List<Question>

) :
    RecyclerView.Adapter<OptionAdapter.MyViewHolder>() {
    var data: ArrayList<OptionModel> = ArrayList()
    var serialno: String = ""
    var correctans: String = ""
    var selectedans: String = ""
    fun updateData(data: ArrayList<OptionModel>) {
        this.data = data
        notifyDataSetChanged()
        Log.e("data", "data")
    }


    fun correctAns(correctans: String, selectedans: String) {
        this.correctans = correctans
        this.selectedans = selectedans
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_options, parent, false)
        /* itemView.layoutParams = ViewGroup.LayoutParams(
             (parent.width * 0.5).toInt(),
             ViewGroup.LayoutParams.MATCH_PARENT
         )*/
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataModel = list.get(position)
        var select = (position + 1).toString()



        getIds.getId(que_id, sub_id)
        Log.e("correct an--", "--" + correctans + "sele--" + selectedans)
        Log.e("list in optn--", "--" + Gson().toJson(list))
        Log.e("list in quelist--", "--" + Gson().toJson(quelist))
        /*if (list.get(position).correct) {
            holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
        }*/

        if (correctans.equals("") && selectedans.equals("")) {





            if (list.get(position).selected) {



//new code
                  holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_dgrey_stroke)
            } else {
                holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke)
            }


        } else {
            if (correctans.equals(select)) {
                holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
            } else if (list.get(position).correct) {
                holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
            }


            /*else if (correctans.equals(selectedans)){
            holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
        }*/ else if (!correctans.equals(selectedans) && selectedans.equals(select)) {
                holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_orange_stroke)
            } else {
                holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke)
            }
        }


        // getll.getLL(holder.ll_option)

        /* if (list.get(position).selected){
             if (selectedans.equals(correctans)){
                 holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
             }else{





                 if (correctans.equals(select)){
                     holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
                  }else{
                     holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_orange_stroke)
                 }

             }
         }else{
             holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke)
         }*/


/*if (list.get(position).selected){
    holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_dgrey_stroke)
}*/


        /*  if (list.get(position).selected && list.get(position).correct) {
              holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_cp_stroke)
          } else if (list.get(position).selected && !list.get(position).correct) {
              holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_orange_stroke)
          } else if (list.get(position).selected) {
              holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke_dgrey_stroke)
          } else {
              holder.ll_option.setBackgroundResource(R.drawable.semi_round_ed_stroke)
          }
  */

        when (position) {
            0 -> {
                serialno = "A"
            }
            1 -> {
                serialno = "B"
            }
            2 -> {
                serialno = "C"
            }
            3 -> {
                serialno = "D"
            }
            4 -> {
                serialno = "E"
            }
        }


        holder.ll_option.setOnClickListener {
            setSelected(position)
            getcorrectoption.getId((position + 1).toString(), que_id, sub_id)
            //submitInterface.onSubmitClick(list)
        }
        holder.tv_ans.setText(dataModel.name)
        holder.tv_serial.setText(serialno)
    }

    fun setCorrect(position: Int) {

        list.get(position).correct = true

    }

    fun setSelected(position: Int) {

        for (muser: OptionModel in list) {
            muser.selected = false
        }

        list.get(position).selected = true
        notifyDataSetChanged()
    }

    /*   fun setIsCorrect(position: Int) {

           for (muser: OptionModel in list) {
               muser.correct = false
           }

           list.get(position).correct = true
   notifyDataSetChanged()
       }*/

    override fun getItemCount(): Int {
        return list.size

    }


    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null

        var tv_ans: TextView = view!!.findViewById(R.id.tv_ans)
        var tv_serial: TextView = view!!.findViewById(R.id.tv_serial)
        var ll_option: LinearLayout = view!!.findViewById(R.id.ll_option)


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