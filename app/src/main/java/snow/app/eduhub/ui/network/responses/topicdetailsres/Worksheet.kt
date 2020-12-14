package snow.app.eduhub.ui.network.responses.topicdetailsres


import com.google.gson.annotations.SerializedName

data class Worksheet(
    @SerializedName("answer_pdf")
    val answerPdf: String,
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("topic_id")
    val topicId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("worksheet_des")
    val worksheetDes: String,
    @SerializedName("worksheet_pdf")
    val worksheetPdf: String,
    @SerializedName("worksheet_status")
    val worksheetStatus: Int,
    @SerializedName("worksheet_title")
    val worksheetTitle: String
)