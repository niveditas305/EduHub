package snow.app.eduhub.ui.network.responses.getChapters


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("chapter")
    val chapter: List<Chapter>,
    @SerializedName("continue_topic")
    val continueTopic: ContinueTopic
)