package snow.app.eduhub.ui.network.responses.topicdetails


import com.google.gson.annotations.SerializedName

data class TopicPdf(
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pdf_description")
    val pdfDescription: String,
    @SerializedName("pdf_name")
    val pdfName: String,
    @SerializedName("pdf_path")
    val pdfPath: String,
    @SerializedName("pdf_status")
    val pdfStatus: String,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_first_name")
    val teacherFirstName: String,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("teacher_last_name")
    val teacherLastName: String,
    @SerializedName("teacher_rating")
    val teacherRating: String,
    @SerializedName("topic_id")
    val topicId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)