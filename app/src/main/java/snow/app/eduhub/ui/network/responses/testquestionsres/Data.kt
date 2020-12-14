package snow.app.eduhub.ui.network.responses.testquestionsres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("questions")
    val questions: List<Question>,
    @SerializedName("questions_count")
    val questionsCount: Int
)