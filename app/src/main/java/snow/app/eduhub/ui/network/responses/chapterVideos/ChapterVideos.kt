package snow.app.eduhub.ui.network.responses.chapterVideos


import com.google.gson.annotations.SerializedName

data class ChapterVideos(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)