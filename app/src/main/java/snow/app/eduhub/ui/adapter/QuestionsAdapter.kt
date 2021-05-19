package snow.app.eduhub.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.http.POST
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityTestSeriesBinding
import snow.app.eduhub.ui.network.responses.testquestionsres.Question
import snow.app.eduhub.util.*


class QuestionsAdapter(
    var contxt: Context,
    var list: List<Question>,
    var getPositionOnBackInterface: GetPositionOnBackInterface,
    var getcorrectoption: GetIdfromAdapter,
    var submitInterface: SubmitInterface,
    var getIds: GetIds,
    var binding: ActivityTestSeriesBinding,
    var getQueList: GetQueList
) : RecyclerView.Adapter<QuestionsAdapter.MyViewHolder>() {
    var ans_optionlist: ArrayList<String> = ArrayList()
    var count = 0
    var optionAdapter: OptionAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_questions, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val dataModel = list.get(position)
        getPositionOnBackInterface.likeClick(position, list)
        Log.e("pos in ", "que adapter---" + position)
        count = position + 1
      optionAdapter?.setCorrect(list.get(position).correctAnswere.toInt()-1)
        /* holder.itemView.findViewById<LinearLayout>(R.id.rv_parent).setOnClickListener {
             val intent = Intent(contxt, TestSeriesActivity::class.java)
             intent.putExtra("teacherId", dataModel.teacherId.toString())
             contxt.startActivity(intent)
         }*/

        holder.tv_question_number.setText("Q. " + dataModel.questionNumber.toString())
        holder.tv_question.setText(dataModel.question)
        ans_optionlist.clear()
        ans_optionlist.add(dataModel.answerOne)
        ans_optionlist.add(dataModel.answerTwo)
        ans_optionlist.add(dataModel.answerThree)
        ans_optionlist.add(dataModel.answerFour)
        ans_optionlist.add(dataModel.answerFive)
        setTopicAdapter(
            holder.rv_options,
            ans_optionlist,
            dataModel.id.toString(),position,
            dataModel.subjectId.toString()
        )

        if (list.get(position).isSubmitted) {
          //  optionAdapter?.setIsCorrect(position)
        }

    }


    override fun getItemCount(): Int {
        return list.size

        // return if (list != null) list.size else 0
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null


        var tv_question_number: TextView = view!!.findViewById(R.id.tv_question_number)
        var tv_question: TextView = view!!.findViewById(R.id.tv_question)
        var rv_options: RecyclerView = view!!.findViewById(R.id.rv_options)

    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        // holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        //  holder.unbind()
    }


    fun setTopicAdapter(
        rv_topics: RecyclerView,
        listt: ArrayList<String>,
        que_id: String,
        position: Int,
        sub_id: String
    ) {
        var optionModel: OptionModel? = null
        var optionlist: ArrayList<OptionModel> = ArrayList()
        val linearLayoutManagertut = LinearLayoutManager(contxt)
        rv_topics.layoutManager = linearLayoutManagertut

        for (i in 0 until listt.size) {
            optionModel = OptionModel(false, listt.get(i), false,
                (list.get(position).correctAnswere.toInt()-1).toString(),"","")
            optionlist.add(optionModel)
        }

        optionAdapter = OptionAdapter(
            contxt, optionlist, getcorrectoption, que_id, sub_id, submitInterface, getIds
        ,list)
        rv_topics.adapter = optionAdapter
    }

    fun callOptionAdapter(optionlist: ArrayList<OptionModel>) {
        optionAdapter?.updateData(optionlist)
    }


    fun updatecorrectselected(correctans: String, selectedans: String) {
        optionAdapter?.correctAns(correctans, selectedans)
    }


    fun setIsSubmitted(position: Int) {

        /*  for (muser: Question in list) {
            muser.isSubmitted = false
          }*/

        list.get(position).isSubmitted = true
        getQueList.getQueLIst(list)
        //   notifyDataSetChanged()
    }


    fun getIsSubmitted(position: Int): Boolean {
        var issub: Boolean = list.get(position).isSubmitted
        return issub
    }
}