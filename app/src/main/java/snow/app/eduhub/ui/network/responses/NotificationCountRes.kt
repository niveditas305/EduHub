package snow.app.eduhub.ui.network.responses


import com.google.gson.annotations.SerializedName

data class NotificationCountRes(
    @SerializedName("count")
    val count: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)