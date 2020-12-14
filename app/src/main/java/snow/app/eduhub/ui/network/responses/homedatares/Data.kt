package snow.app.eduhub.ui.network.responses.homedatares


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banner")
    val banner: List<Banner>,
    @SerializedName("continue_topic")
    val continueTopic: ContinueTopic,
    @SerializedName("recent_learn_topic")
    val recentLearnTopic: RecentLearnTopic,
    @SerializedName("subject")
    val subject: List<Subject>,
    @SerializedName("top_teacher")
    val topTeacher: List<TopTeacher>,
    @SerializedName("top_topics")
    val topTopics: List<TopTopic>
)