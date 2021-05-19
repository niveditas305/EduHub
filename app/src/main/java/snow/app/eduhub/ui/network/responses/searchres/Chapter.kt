package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("chapter_description")
    var chapterDescription: String,
    @SerializedName("chapter_image")
    var chapterImage: String,
    @SerializedName("chapter_name")
    var chapterName: String,
    @SerializedName("chapter_status")
    var chapterStatus: Int,
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
    @SerializedName("topic")
    var topic: List<Topic>,
    @SerializedName("topic_count")
    var topicCount: Int,
    @SerializedName("updated_at")
    var updatedAt: String
)