package snow.app.eduhub.ui.network.responses.fetchsubjectlist


import com.google.gson.annotations.SerializedName

data class SubjectList(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)