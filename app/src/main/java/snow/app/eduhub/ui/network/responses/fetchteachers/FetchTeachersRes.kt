package snow.app.eduhub.ui.network.responses.fetchteachers


import com.google.gson.annotations.SerializedName

data class FetchTeachersRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)