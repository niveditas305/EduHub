package snow.app.eduhub.ui.network.responses.getstudyguild


import com.google.gson.annotations.SerializedName

data class StudyGuideRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)