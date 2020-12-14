package snow.app.eduhub.ui.network.responses.getChapters


import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("chapter_description")
    val chapterDescription: String,
    @SerializedName("chapter_image")
    val chapterImage: String,
    @SerializedName("chapter_name")
    val chapterName: String,
    @SerializedName("chapter_status")
    val chapterStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("topic")
    val topic: List<Topic>,
    @SerializedName("topic_count")
    val topicCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)