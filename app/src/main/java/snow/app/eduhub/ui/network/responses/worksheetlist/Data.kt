package snow.app.eduhub.ui.network.responses.worksheetlist


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("answer_pdf")
    var answerPdf: String,
    @SerializedName("chapter_id")
    var chapterId: Int,
    @SerializedName("chapter_name")
    var chapterName: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("created_date")
    var createdDate: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("school_class_id")
    var schoolClassId: Int,
    @SerializedName("subject_id")
    var subjectId: Int,
    @SerializedName("subject_name")
    var subjectName: String,

    @SerializedName("topic_name")
    var topicName: String,
    @SerializedName("teacher_id")
    var teacherId: Int,
    @SerializedName("teacher_name")
    var teacherName: String,
    @SerializedName("topic_id")
    var topicId: Int,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("updated_date")
    var updatedDate: String,
    @SerializedName("worksheet_des")
    var worksheetDes: String,
    @SerializedName("worksheet_pdf")
    var worksheetPdf: String,
    @SerializedName("worksheet_status")
    var worksheetStatus: Int,
    @SerializedName("worksheet_time")
    var worksheetTime: Long,
    @SerializedName("worksheet_title")
    var worksheetTitle: String
)