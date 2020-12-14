package snow.app.eduhub.network.responses.topPicks

data class Data(
    val chapter_description: String,
    val chapter_id: Int,
    val chapter_image: String,
    val chapter_name: String,
    val created_at: String,
    val id: Int,
    val school_class_id: Int,
    val student_id: Int,
    val subject_id: Int,
    val updated_at: String
)