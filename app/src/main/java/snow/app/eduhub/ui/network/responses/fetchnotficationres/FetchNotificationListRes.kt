package snow.app.eduhub.ui.network.responses.fetchnotficationres


import com.google.gson.annotations.SerializedName

data class FetchNotificationListRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)