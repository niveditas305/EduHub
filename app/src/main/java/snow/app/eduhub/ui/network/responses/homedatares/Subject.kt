package snow.app.eduhub.ui.network.responses.homedatares


import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("school_class_id")
    val schoolClassId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subject_image")
    val subjectImage: String,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("updated_at")
    val updatedAt: String
)