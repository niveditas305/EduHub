package snow.app.eduhub.ui.network.responses.topicdetailsres


import com.google.gson.annotations.SerializedName

data class GetTopicDetailsRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)