package snow.app.eduhub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.StudyGuideActivity
import snow.app.eduhub.ui.network.responses.getstudyguild.Data
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.PdfClickInterface


class StudyGuideVideoAdapter(var contxt: Context, var list: List<Data>,
                             val pdfListenr: PdfClickInterface
) :
    RecyclerView.Adapter<StudyGuideVideoAdapter.MyViewHolder>() {
    var data: List<String> = ArrayList()
    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_tutorials_horizontal, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataModel = list.get(position)

        /*     val dataModel = data[position]
             holder.setViewModel(HomeRowVm())
             Log.e("onBindViewHolder", "data")*/

        /* holder.itemView.findViewById<LinearLayout>(R.id.ll_parent).setOnClickListener {
               contxt.startActivity(Intent(contxt, PostedJobView::class.java))
           }*/


        holder.tv_tut_name.setText(dataModel.videoName)
        holder.tv_tut_des.setText(dataModel.videoDescription)
        AppUtils.ImageWithGlide(holder.iv_video_thumb, dataModel.videoThumbnail)

        holder.iv_play.setOnClickListener {

            if ((contxt as StudyGuideActivity).isNetworkConnected()) {
                pdfListenr.onSubmitClick(dataModel.videoPath,"video")

               /* val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.videoPath));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // intent.setPackage("com.google.android.youtube");
                contxt.startActivity(intent)*/



            } else {
                Toast.makeText(contxt, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        // var binding: RowHomebind? = null


        var iv_video_thumb: ImageView = view!!.findViewById(R.id.iv_video_thumb)
        var iv_play: ImageView = view!!.findViewById(R.id.iv_play)
        var tv_tut_name: TextView = view!!.findViewById(R.id.tv_tut_name)
        var tv_tut_des: TextView = view!!.findViewById(R.id.tv_tut_des)


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