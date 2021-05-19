package snow.app.eduhub.ui.network.responses.topicdetailsres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("videos")
    val videos: List<Video>,
    @SerializedName("worksheets")
    val worksheets: List<Worksheet>,
    @SerializedName("rating_status")
    val rating_status: String
)