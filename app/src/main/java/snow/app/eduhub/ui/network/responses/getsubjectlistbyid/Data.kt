package snow.app.eduhub.ui.network.responses.getsubjectlistbyid


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("subject_image")
    val subjectImage: String,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("updated_at")
    val updatedAt: String
)