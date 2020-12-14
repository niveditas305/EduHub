package snow.app.eduhub.ui.network.responses.forgotpassres


import com.google.gson.annotations.SerializedName

data class ForgotPassRes(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)