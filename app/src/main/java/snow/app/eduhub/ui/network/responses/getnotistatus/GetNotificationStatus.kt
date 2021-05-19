package snow.app.beautasapserviceprovider.network.responses.signupres.getnotistatus


import com.google.gson.annotations.SerializedName

data class GetNotificationStatus(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)