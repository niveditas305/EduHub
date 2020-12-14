package snow.app.eduhub.network.responses.getSubjects

data class Data(
    val created_at: String,
    val id: Int,
    val school_class_id: Int,
    val status: Int,
    val subject_name: String,
    val updated_at: String,
    val subject_image:String
)