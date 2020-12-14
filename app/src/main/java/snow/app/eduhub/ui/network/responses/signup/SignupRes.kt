package snow.app.eduhub.ui.network.responses.signup


import com.google.gson.annotations.SerializedName

data class SignupRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)