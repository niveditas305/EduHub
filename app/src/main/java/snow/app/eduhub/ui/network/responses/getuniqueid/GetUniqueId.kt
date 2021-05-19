package snow.app.eduhub.ui.network.responses.getuniqueid


import com.google.gson.annotations.SerializedName

data class GetUniqueId(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("test_id")
    val testId: String,
    @SerializedName("test_unique_id")
    val testUniqueId: String
)