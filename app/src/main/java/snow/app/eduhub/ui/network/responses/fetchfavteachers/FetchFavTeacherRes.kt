package snow.app.eduhub.ui.network.responses.fetchfavteachers


import com.google.gson.annotations.SerializedName

data class FetchFavTeacherRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)