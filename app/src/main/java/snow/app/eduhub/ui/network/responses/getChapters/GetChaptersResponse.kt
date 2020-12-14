package snow.app.eduhub.ui.network.responses.getChapters


import com.google.gson.annotations.SerializedName

data class GetChaptersResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)