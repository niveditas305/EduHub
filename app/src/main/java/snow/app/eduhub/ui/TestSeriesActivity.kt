package snow.app.eduhub.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_test_series.*
import kotlinx.android.synthetic.main.test_quit_dialog.view.*
import kotlinx.android.synthetic.main.time_finish_dialog.view.*
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityTestSeriesBinding
import snow.app.eduhub.ui.adapter.QuestionsAdapter
import snow.app.eduhub.ui.network.responses.testquestionsres.Question
import snow.app.eduhub.util.*
import snow.app.eduhub.viewmodels.QuestionsVm
import java.util.concurrent.TimeUnit


class TestSeriesActivity : BaseActivity(), GetPositionOnBackInterface, GetIdfromAdapter, GetIds,
    SubmitInterface, GetQueList {

    lateinit var binding: ActivityTestSeriesBinding
    lateinit var viewModel: QuestionsVm
    var linearLayout_a: LinearLayout? = null
    var countDownTimer: CountDownTimer? = null
    var submitbuttonclicked = false
    var itemPos: Int = 0
    var optionlist: ArrayList<OptionModel> = ArrayList()
    var queList: List<Question> = ArrayList()
    var linearLayoutManagertut: LinearLayoutManager? = null
    var questionsAdapter: QuestionsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_test_series)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_series)
        binding.lifecycleOwner = this
        viewModel = QuestionsVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        linearLayoutManagertut =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.ivBac.setOnClickListener {
            onBackPressed()
        }


        /*  tv_submit.setOnClickListener {
              startActivity(Intent(this,TestSummary::class.java))
          }*/

        if (intent.hasExtra("testId")) {
            viewModel.teacherId.set(intent.getStringExtra("teacherId").toString())
            viewModel.test_id.set(intent.getStringExtra("testId").toString())
            viewModel.testname.set(intent.getStringExtra("testname").toString())
        }

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel.isError.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })



        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    viewModel.ques_count_value.set("Question 1 to " + it.data.questionsCount.toString())


                    queList = it.data.questions
                    if (it.data.questions.size > 0) {
                        binding.rvQuestions.layoutManager = linearLayoutManagertut
                        questionsAdapter =
                            QuestionsAdapter(
                                this,
                                it.data.questions,
                                getquePos,
                                mCallback,
                                mCallback_onsubmitclick,
                                mCallback_ids, binding, getQueList
                            )


                        binding.rvQuestions.adapter = questionsAdapter
                        binding.rvQuestions.setOnTouchListener(object : View.OnTouchListener {
                            override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                                return true
                            }


                        })
                        /*  binding.rvQuestions.addOnItemTouchListener(disabler);
                           binding.rvQuestions.adapter = questionsAdapter
   */



                        binding.rv.visibility = View.VISIBLE
                        binding.tvTimeRe.visibility = View.VISIBLE

                        if (it.data.test_time != 0 || it.data.test_time != null) {

                            if (it.data.test_time > 0) {
                                startCountDown(binding.tvTimeRe, it.data.test_time.toLong())
                            }else{
                                binding.tvTimeRe.setText("Time:No time limit.")
                            }
                        } else {
                            binding.tvTimeRe.setText("Time:No time limit.")
                        }


                    } else {
                        //  binding.llTwo.visibility = View.GONE
                    }
                } else {
                    Log.e("statusfalse", "login--")
                    /* binding.tvNoRecordFound.visibility = View.VISIBLE
                     binding.llTwo.visibility = View.GONE*/
                    //  showError(it.message, this)
                }
            }

        })




        binding.tvTestsummary.setOnClickListener {
            var intent = Intent(this, TestSummary::class.java)
            intent.putExtra("testid", viewModel.test_id.get().toString())
            intent.putExtra("test_unique_id", viewModel.test_unique_id.get().toString())
            startActivity(intent)
            finish()
        }

        viewModel.respData_getUniqueId.observe(this, Observer {
            if (it != null) {
                if (it.status) {
                    viewModel.test_unique_id.set(it.testUniqueId.toString())
                    viewModel.testQuestionsList()
                } else {
                    Log.e("statusfalse", "login--")

                }
            }

        })
        viewModel.respData_submitans.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    if (it.chooseOption != null) {
                        viewModel.api_solution.set(it.solution)
                        questionsAdapter?.updatecorrectselected(it.rightOption, it.chooseOption)
                        questionsAdapter?.optionAdapter?.setCorrect(it.rightOption.toInt() - 1)

                        Log.e("it.rightOption", "---" + (it.rightOption.toInt() - 1) + "")
                        if (it.chooseOption.equals(it.rightOption)) {
                            // questionsAdapter?.optionAdapter?.setIsCorrect(it.rightOption.toInt())
                            questionsAdapter?.optionAdapter?.setCorrect(it.rightOption.toInt() - 1)
                            binding.llRightAns.visibility = View.VISIBLE


                            if (viewModel.last_pos_rv.get().toString().equals("last")) {
                                binding.tvTestsummary.visibility = View.VISIBLE
                                binding.tvSummary.visibility = View.GONE
                            } else {
                                /*  binding.tvSummary.visibility = View.VISIBLE
                                  binding.tvTestsummary.visibility = View.GONE*/
                                binding.tvSummary.visibility = View.VISIBLE
                                binding.tvSubmit.visibility = View.GONE
                                binding.tvTestsummary.visibility = View.GONE
                                questionsAdapter?.setIsSubmitted(linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition())
                            }

                            binding.llWrongAns.visibility = View.GONE

                            for (i in 0 until optionlist.size) {
                                //  print("option list ${optionlist[i].selected}")
                                if (optionlist[i].selected) {
                                    optionlist[i].correct = true

                                }
                                Log.e("option list ", "---" + optionlist[i].selected)
                            }


                        } else {
                            Log.e(
                                "last_pos_rv ",
                                "---" + viewModel.last_pos_rv.get().toString() + ""
                            )
                            for (i in 0 until optionlist.size) {
                                //  print("option list ${optionlist[i].selected}")
                                if (optionlist[i].selected) {
                                    optionlist[i].correct = false
                                }
                                Log.e("option list ", "---" + optionlist[i].selected)

                            }

                            if (viewModel.last_pos_rv.get().toString().equals("last")) {
                                binding.tvTestsummary.visibility = View.VISIBLE
                                binding.tvSummary.visibility = View.GONE
                            } else {
                                /*  binding.tvSummary.visibility = View.VISIBLE
                                  binding.tvTestsummary.visibility = View.GONE*/
                                binding.tvSummary.visibility = View.VISIBLE
                                binding.tvSubmit.visibility = View.GONE
                                binding.tvTestsummary.visibility = View.GONE
                                questionsAdapter?.setIsSubmitted(linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition())
                            }
                            // binding.tvSummary.visibility = View.VISIBLE
                            binding.llWrongAns.visibility = View.VISIBLE
                            binding.llRightAns.visibility = View.GONE
                        }
                        // questionsAdapter?.callOptionAdapter(optionlist)

                    }


                } else {
                    Log.e("statusfalse", "login--")

                }
            }

        })
        binding.ivNext.setOnClickListener {


            if (queList.size > 0) {


                itemPos = linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition() + 1
                var tempPos = itemPos
                tempPos = tempPos++
                Log.e("pos in ivNext", "--" + tempPos)

                if (itemPos == queList.size - 1) {
                    binding.ivNext.visibility = View.GONE
                    if (queList.get(tempPos).isSubmitted) {
                        binding.tvTestsummary.visibility = View.VISIBLE
                        binding.tvSubmit.visibility = View.GONE
                        binding.tvSummary.visibility = View.GONE
                    } else {
                        binding.tvSummary.visibility = View.GONE
                        binding.tvSubmit.visibility = View.VISIBLE
                    }
                } else {

                    binding.tvTestsummary.visibility = View.GONE   //by me
                    binding.ivNext.visibility = View.VISIBLE
                    if (queList.get(tempPos) != null) {
                        if (queList.get(tempPos).isSubmitted) {


                            binding.tvSummary.visibility = View.VISIBLE
                            binding.tvSubmit.visibility = View.GONE
                        } else {
                            binding.tvSummary.visibility = View.GONE
                            binding.tvSubmit.visibility = View.VISIBLE
                        }
                    } else {
                        binding.tvSummary.visibility = View.GONE
                        binding.tvSubmit.visibility = View.VISIBLE
                    }
                }


                /*  if (!submitbuttonclicked) {
                      viewModel.choose_option.set("")
                      viewModel.attempt_status.set("0")
                      viewModel.submitAns(it)
                  }*/
                viewModel.choose_option.set("")
                viewModel.question_id.set("")
                viewModel.subject_id.set("")
                binding.llRightAns.visibility = View.GONE
                binding.llWrongAns.visibility = View.GONE
                binding.llSolution.visibility = View.GONE
                // binding.tvSummary.visibility = View.GONE
                if (linearLayoutManagertut?.findLastCompletelyVisibleItemPosition()!! < (questionsAdapter?.getItemCount()!!
                        .minus(1))
                ) {
                    linearLayoutManagertut?.scrollToPosition(linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition() + 1);
                }
                //    binding.ivPrevious.visibility = View.VISIBLE

            }
        }

        binding.ivPrevious.setOnClickListener {
            /*  if (linearLayout_a?.background==ContextCompat.getDrawable(this@TestSeriesActivity,R.drawable.semi_round_ed_stroke_cp_stroke)){
                  binding.tvSubmit.visibility=View.GONE
                  binding.tvSummary.visibility=View.VISIBLE
              } else{
                  binding.tvSubmit.visibility=View.VISIBLE
                  binding.tvSummary.visibility=View.GONE
              }
  */
            itemPos = linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition() - 1

            var tempPos = itemPos
            tempPos = tempPos--
            Log.e("pos in ivPrevious", "--" + tempPos)
            if (queList.size > 0) {
                if (queList.get(tempPos).isSubmitted) {
                    binding.tvSummary.visibility = View.VISIBLE
                    binding.tvSubmit.visibility = View.GONE
                } else {
                    binding.tvSummary.visibility = View.GONE
                    binding.tvSubmit.visibility = View.VISIBLE
                }
            }

            /*  if (questionsAdapter!!.getIsSubmitted(viewModel.rv_item_pos.get()!!.toInt())) {
                   binding.tvSummary.visibility = View.VISIBLE
                   binding.tvSubmit.visibility = View.GONE
               } else {
                   binding.tvSummary.visibility = View.GONE
                   binding.tvSubmit.visibility = View.VISIBLE
               }*/

            /*  Log.e(
                  "viewModel.rv_item_pos",
                  "---" + viewModel.rv_item_pos.get().toString() + "---true or false--" +
                          questionsAdapter!!.getIsSubmitted(viewModel.rv_item_pos.get()!!.toInt())
              )*/
            // var pos=linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition() - 1
            if (linearLayoutManagertut?.findLastCompletelyVisibleItemPosition()!! < (questionsAdapter?.getItemCount()!!
                    .plus(1))
            ) {
                linearLayoutManagertut?.scrollToPosition(linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition() - 1);
            }
            if (linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition() == 1) {
                binding.ivPrevious.visibility = View.GONE
            }
            binding.ivNext.visibility = View.VISIBLE

            binding.tvTestsummary.visibility = View.GONE   //by me
            binding.llWrongAns.visibility = View.GONE   //by me
            binding.llRightAns.visibility = View.GONE   //by me

        }

        if (isNetworkConnected()) {


            viewModel.getUniqueId()

        } else {
            showInternetToast()
        }


        binding.tvSubmit.setOnClickListener {


            if (queList.size > 0) {

                if (viewModel.choose_option.get().toString().isEmpty()) {
                    Toast.makeText(
                        this,
                        "Please select option to submit answer",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    submitbuttonclicked = true
                    viewModel.submitAns(it)


                    questionsAdapter?.setIsSubmitted(linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition())


                    Log.e("queList", "--" + Gson().toJson(queList))
                    Log.e(
                        "position on btn click",
                        "--" + linearLayoutManagertut!!.findLastCompletelyVisibleItemPosition()
                    )

                }


            }
        }

        binding.tvSummary.setOnClickListener {
            binding.llSolution.visibility = View.VISIBLE
            binding.tvSolution.setText("Solution: " + viewModel.api_solution.get().toString())
            binding.llWrongAns.visibility = View.GONE
            binding.llRightAns.visibility = View.GONE
        }


    }

    override fun likeClick(pos: Int, list: List<Question>) {

    }

    val mCallback = object : GetIdfromAdapter {
        override fun getId(index: String, que_id: String, sub_id: String) {
            viewModel.choose_option.set(index)
            viewModel.question_id.set(que_id)
            viewModel.subject_id.set(sub_id)
        }
    }

    val getquePos = object : GetPositionOnBackInterface {

        override fun likeClick(pos: Int, list: List<Question>) {
            // viewModel.rv_item_pos.set(pos)
            //   itemPos=pos
/*
            if (pos == 0) {
                binding.ivPrevious.visibility = View.GONE
            } else {
                binding.ivPrevious.visibility = View.VISIBLE
            }*/
            if (pos == list.size - 1) {
                binding.ivNext.visibility = View.GONE
                viewModel.last_pos_rv.set("last")
            }
        }

    }

    val mCallback_ids = object : GetIds {
        override fun getId(que_id: String, sub_id: String) {
            viewModel.question_id.set(que_id)
            viewModel.subject_id.set(sub_id)
        }
    }


    val mCallback_onsubmitclick = object : SubmitInterface {
        override fun onSubmitClick(list: ArrayList<OptionModel>) {
            optionlist.clear()
            optionlist = list
        }
    }
    val getQueList = object : GetQueList {
        override fun getQueLIst(arrayList: List<Question>) {

            queList = arrayList
        }
    }


    override fun getId(index: String, que_id: String, sub_id: String) {
    }

    override fun onSubmitClick(list: ArrayList<OptionModel>) {
    }

    override fun getId(que_id: String, sub_id: String) {

    }


    override fun onBackPressed() {

        quitTest()
    }


    fun startCountDown(_tv: TextView, time: Long/*, item: Data*/) {
        val minutes = time
        val milliseconds = (minutes * 60000).toLong()
        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            // adjust the milli seconds here
            override fun onTick(millisUntilFinished: Long) {
                _tv.setText(
                    "Time remaining: " + java.lang.String.format(
                        "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                        millisUntilFinished
                                    )
                                )
                    )
                )

// Vibrate for 500 milliseconds
                // if (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) == 0L && TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
// TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) == 55L) {
// val v: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
// // Vibrate for 500 milliseconds
// v.vibrate(60000)
// }

            }

            override fun onFinish() {
                if (!isFinishing) {
                    timefinish()
                }
                // countDownTimer!!.cancel();
                /*   if (data.size > 0) {
                       data.remove(item)
                       //   (context as BookingsScreen).refreshActivityWithoutAnimation()
                   }*/
                //    countDownTimer!!.cancel();
                //   bookingFragment.refreshFRagmentwithoutANimation()
            }
        }.start()
    }

    private fun timefinish() {
        val mDialogView =
            LayoutInflater.from(this@TestSeriesActivity).inflate(R.layout.time_finish_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this@TestSeriesActivity)
            .setView(mDialogView)
        /// .setTitle("Login Form")
        //show dialog
        val mAlertDialogg = mBuilder.show()
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(mAlertDialogg.getWindow()!!.getAttributes())
        lp.gravity = Gravity.CENTER
        mAlertDialogg!!.getWindow()!!.setAttributes(lp)
        mAlertDialogg.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mAlertDialogg.getWindow()!!.setGravity(Gravity.CENTER);
        //cancel button click of custom layout


        mDialogView.btn_yes_u.setOnClickListener {
            mAlertDialogg.dismiss()
            var intent = Intent(this@TestSeriesActivity, TestSummary::class.java)
            intent.putExtra("testid", viewModel.test_id.get().toString())
            intent.putExtra("test_unique_id", viewModel.test_unique_id.get().toString())


            startActivity(intent)
            finish()
        }

    }


    private fun quitTest() {

        val mDialogView =
            LayoutInflater.from(this@TestSeriesActivity).inflate(R.layout.test_quit_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this@TestSeriesActivity)
            .setView(mDialogView)
        /// .setTitle("Login Form")
        //show dialog
        val mAlertDialogg = mBuilder.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(mAlertDialogg.getWindow()!!.getAttributes())
        lp.gravity = Gravity.CENTER



        mAlertDialogg!!.getWindow()!!.setAttributes(lp)
        mAlertDialogg.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mAlertDialogg.getWindow()!!.setGravity(Gravity.CENTER);
        //cancel button click of custom layout


        mDialogView.btn_yes.setOnClickListener {
            mAlertDialogg.dismiss()
            var intent = Intent(this@TestSeriesActivity, TestSummary::class.java)
            intent.putExtra("testid", viewModel.test_id.get().toString())
            intent.putExtra("test_unique_id", viewModel.test_unique_id.get().toString())
            startActivity(intent)
            finish()
        }


        mDialogView.btn_cancel.setOnClickListener {
            mAlertDialogg.dismiss()

        }

    }


    override fun getQueLIst(arrayList: List<Question>) {

    }

}