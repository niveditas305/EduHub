package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class SearchRes(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean
)