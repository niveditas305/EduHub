package snow.app.eduhub.ui.network.responses.subjectsres


import com.google.gson.annotations.SerializedName

data class SubjectRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)