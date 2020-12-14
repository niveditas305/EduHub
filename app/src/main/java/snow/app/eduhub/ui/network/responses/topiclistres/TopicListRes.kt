package snow.app.eduhub.ui.network.responses.topiclistres


import com.google.gson.annotations.SerializedName

data class TopicListRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)