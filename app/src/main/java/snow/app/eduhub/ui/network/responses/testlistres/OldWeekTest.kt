package snow.app.eduhub.ui.network.responses.testlistres


import com.google.gson.annotations.SerializedName

data class OldWeekTest(
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("school_class_id")
    val schoolClassId: Int,


    @SerializedName("test_attempt_status")
    val test_attempt_status: Int,
    @SerializedName("set_description")
    val setDescription: String,
    @SerializedName("set_name")
    val setName: String,
    @SerializedName("set_status")
    val setStatus: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("topic_id")
    val topicId: Int,
    @SerializedName("updated_at")
    val updatedAt: String ,
    @SerializedName("last_attempt_score")
    val last_attempt_score: String ,
    @SerializedName("passing_marks")
    val passing_marks: String
    ,@SerializedName("chapter_name")
    val chapter_name: String,


    @SerializedName("topic_name")
    val topic_name: String
)