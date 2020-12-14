package snow.app.eduhub.ui.network.responses.testquestionsres


import com.google.gson.annotations.SerializedName

data class TestQuestionsRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)