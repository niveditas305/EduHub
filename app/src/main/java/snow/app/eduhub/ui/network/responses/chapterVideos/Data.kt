package snow.app.eduhub.ui.network.responses.chapterVideos


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("chapter_id")
    val chapterId: Int,
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
    @SerializedName("topic_id")
    val topicId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_description")
    val videoDescription: String,
    @SerializedName("video_name")
    val videoName: String,
    @SerializedName("video_path")
    val videoPath: String,
    @SerializedName("video_status")
    val videoStatus: Int,
    @SerializedName("video_thumbnail")
    val videoThumbnail: String
)