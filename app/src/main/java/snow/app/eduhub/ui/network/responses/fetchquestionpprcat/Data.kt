package snow.app.eduhub.ui.network.responses.fetchquestionpprcat


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("category_name")
    val categoryName: String,
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
    @SerializedName("updated_at")
    val updatedAt: String
)