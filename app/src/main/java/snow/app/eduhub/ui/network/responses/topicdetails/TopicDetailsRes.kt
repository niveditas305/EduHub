package snow.app.eduhub.ui.network.responses.topicdetails


import com.google.gson.annotations.SerializedName

data class TopicDetailsRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)