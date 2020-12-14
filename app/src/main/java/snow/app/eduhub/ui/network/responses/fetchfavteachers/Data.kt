package snow.app.eduhub.ui.network.responses.fetchfavteachers


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("by_student")
    val byStudent: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("teacher_experence")
    val teacherExperence: Int,
    @SerializedName("teacher_first_name")
    val teacherFirstName: String,
    @SerializedName("teacher_image")
    val teacherImage: String,
    @SerializedName("teacher_last_name")
    val teacherLastName: String,
    @SerializedName("teacher_rating")
    val teacherRating: String,
    @SerializedName("teacher_subject")
    val teacherSubject: String,
    @SerializedName("to_teacher")
    val toTeacher: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)