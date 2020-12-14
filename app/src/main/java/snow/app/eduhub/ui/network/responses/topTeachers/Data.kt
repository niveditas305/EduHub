package snow.app.eduhub.network.responses.topTeachers

data class Data(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val teacher_address: String,
    val teacher_contact: String,
    val teacher_description: Any,
    val teacher_email_verification: Any,
    val teacher_first_name: String,
    val teacher_follow: Int,
    val teacher_id_proof: String,
    val teacher_image: String,
    val teacher_last_name: String,
    val teacher_qualification: Any,
    val teacher_rating: String,
    val teacher_status: Int,
    val teacher_subject: String,
    val updated_at: String
)