package snow.app.eduhub.ui.network.responses.fetchprofile


import com.google.gson.annotations.SerializedName

data class FetchProfile(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)