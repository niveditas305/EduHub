package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("chapter")
    var chapter: List<Chapter>,
    @SerializedName("subject_data")
    var subjectData: List<SubjectData>,
    @SerializedName("topic_data")
    var topicData: List<TopicData>
)