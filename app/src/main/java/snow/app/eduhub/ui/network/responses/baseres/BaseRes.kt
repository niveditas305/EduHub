package snow.app.eduhub.ui.network.responses.baseres


import com.google.gson.annotations.SerializedName

data class BaseRes(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)