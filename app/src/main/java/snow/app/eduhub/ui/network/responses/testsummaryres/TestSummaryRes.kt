package snow.app.eduhub.ui.network.responses.testsummaryres


import com.google.gson.annotations.SerializedName

data class TestSummaryRes(
    @SerializedName("accuracy_percentage")
    val accuracyPercentage: Double,
    @SerializedName("answer_pdf")
    val answerPdf: String,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)