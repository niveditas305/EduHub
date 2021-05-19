package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("chapter_id")
    var chapterId: Int,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("school_class_id")
    var schoolClassId: Int,
    @SerializedName("subject_id")
    var subjectId: Int,
    @SerializedName("teacher_id")
    var teacherId: Int,
    @SerializedName("topic_description")
    var topicDescription: Any,
    @SerializedName("topic_name")
    var topicName: String,
    @SerializedName("topic_status")
    var topicStatus: Int,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("views")
    var views: Int
)