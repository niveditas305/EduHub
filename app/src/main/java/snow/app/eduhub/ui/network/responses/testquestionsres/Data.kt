package snow.app.eduhub.ui.network.responses.testquestionsres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("questions")
    val questions: List<Question>,
    @SerializedName("questions_count")
    val questionsCount: Int ,
    @SerializedName("test_time")
    val test_time: Int
)