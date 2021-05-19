package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class SubjectData(
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("school_class_id")
    var schoolClassId: Int,
    @SerializedName("status")
    var status: Int,
    @SerializedName("subject_image")
    var subjectImage: String,
    @SerializedName("subject_name")
    var subjectName: String,
    @SerializedName("updated_at")
    var updatedAt: String
)