package snow.app.eduhub.ui.network.responses.fetchnotficationres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("chapter_name")
    val chapterName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("formatedDate_Time")
    val formatedDateTime: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("notification_type")
    val notificationType: Int,
    @SerializedName("past_question_category_id")
    val pastQuestionCategoryId: Int,
    @SerializedName("past_question_category_name")
    val pastQuestionCategoryName: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("topic_id")
    val topicId: Int,
    @SerializedName("topic_name")
    val topicName: String,
    @SerializedName("updated_at")
    val updatedAt: String ,
    @SerializedName("photo")
    val photo: String
)