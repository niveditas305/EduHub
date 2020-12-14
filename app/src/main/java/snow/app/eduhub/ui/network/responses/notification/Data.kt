package snow.app.eduhub.network.responses.notification

data class Data(
    val created_at: String,
    val device_token: String,
    val email: String,
    val facebook_id: Any,
    val google_id: Any,
    val id: Int,
    val mobile_otp: Any,
    val notification_setup: String,
    val password: String,
    val school_class_id: Int,
    val student_address: Any,
    val student_full_name: String,
    val student_image: Any,
    val student_moblie: String,
    val student_school: String,
    val student_status: Int,
    val updated_at: String
)