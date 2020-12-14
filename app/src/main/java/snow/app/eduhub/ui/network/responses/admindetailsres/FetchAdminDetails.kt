package snow.app.eduhub.ui.network.responses.admindetailsres


import com.google.gson.annotations.SerializedName

data class FetchAdminDetails(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)