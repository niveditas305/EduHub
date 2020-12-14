package snow.app.eduhub.ui.network.responses.teachersprofile


import com.google.gson.annotations.SerializedName

data class TeachersProfileRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)