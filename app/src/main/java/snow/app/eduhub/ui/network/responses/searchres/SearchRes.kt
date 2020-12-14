package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class SearchRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)