package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("continue_topic")
    val continueTopic: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("recent_topic_status")
    val recentTopicStatus: Int,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("topic_description")
    val topicDescription: String,
    @SerializedName("topic_name")
    val topicName: String,
    @SerializedName("topic_status")
    val topicStatus: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("views")
    val views: Int
)