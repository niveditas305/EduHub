package snow.app.eduhub.ui.network.responses.scoreres


import com.google.gson.annotations.SerializedName

data class ScoreListing(
    @SerializedName("attempt_status")
    val attemptStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("question_id")
    val questionId: Int,
    @SerializedName("result_status")
    val resultStatus: Int,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("subject_wise_percentage")
    val subjectWisePercentage: String,
    @SerializedName("test_id")
    val testId: Int,
    @SerializedName("test_percentage")
    val testPercentage: Double,
    @SerializedName("updated_at")
    val updatedAt: String
)