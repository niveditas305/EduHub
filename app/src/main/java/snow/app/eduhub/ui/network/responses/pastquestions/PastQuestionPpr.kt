package snow.app.eduhub.ui.network.responses.pastquestions


import com.google.gson.annotations.SerializedName

data class PastQuestionPpr(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)