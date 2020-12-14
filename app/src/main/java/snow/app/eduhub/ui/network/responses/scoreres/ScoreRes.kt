package snow.app.eduhub.ui.network.responses.scoreres


import com.google.gson.annotations.SerializedName

data class ScoreRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)