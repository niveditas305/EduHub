package snow.app.eduhub.ui.network.responses.lessonlistres


import com.google.gson.annotations.SerializedName

data class LessonListRes(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean
)