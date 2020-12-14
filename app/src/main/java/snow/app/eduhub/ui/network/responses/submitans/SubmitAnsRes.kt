package snow.app.eduhub.ui.network.responses.submitans


import com.google.gson.annotations.SerializedName

data class SubmitAnsRes(
    @SerializedName("choose_option")
    val chooseOption: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result_status")
    val resultStatus: Int,
    @SerializedName("right_option")
    val rightOption: String,
    @SerializedName("solution")
    val solution: String,
    @SerializedName("status")
    val status: Boolean
)