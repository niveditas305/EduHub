package snow.app.eduhub.ui.network.responses.teachersprofile


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("by_student")
    val byStudent: Int,
    @SerializedName("class_name")
    val className: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("review_content")
    val reviewContent: String,
    @SerializedName("review_star")
    val reviewStar: Int,
    @SerializedName("review_status")
    val reviewStatus: Int,
    @SerializedName("student_profile")
    val studentProfile: String,
    @SerializedName("to_teacher")
    val toTeacher: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)