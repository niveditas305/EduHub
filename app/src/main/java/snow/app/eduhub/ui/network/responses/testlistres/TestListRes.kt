package snow.app.eduhub.ui.network.responses.testlistres


import com.google.gson.annotations.SerializedName

data class TestListRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)