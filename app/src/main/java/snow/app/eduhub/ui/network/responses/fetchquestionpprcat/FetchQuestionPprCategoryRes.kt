package snow.app.eduhub.ui.network.responses.fetchquestionpprcat


import com.google.gson.annotations.SerializedName

data class FetchQuestionPprCategoryRes(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)