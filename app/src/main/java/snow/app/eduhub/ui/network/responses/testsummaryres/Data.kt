package snow.app.eduhub.ui.network.responses.testsummaryres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("attempt_status")
    val attemptStatus: Int,
    @SerializedName("question_number")
    val questionNumber: Int,
    @SerializedName("result_status")
    val resultStatus: Int
)