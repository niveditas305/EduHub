package snow.app.eduhub.ui.network.responses.homedatares


import com.google.gson.annotations.SerializedName

data class TopTeacher(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("fav")
    val fav: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("teacher_address")
    val teacherAddress: String,
    @SerializedName("teacher_contact")
    val teacherContact: String,
    @SerializedName("teacher_description")
    val teacherDescription: Any,
    @SerializedName("teacher_email_verification")
    val teacherEmailVerification: Any,
    @SerializedName("teacher_expirence")
    val teacherExpirence: String,
    @SerializedName("teacher_first_name")
    val teacherFirstName: String,
    @SerializedName("teacher_id_proof")
    val teacherIdProof: String,
    @SerializedName("teacher_image")
    val teacherImage: String,
    @SerializedName("teacher_last_name")
    val teacherLastName: String,
    @SerializedName("teacher_qualification")
    val teacherQualification: Any,
    @SerializedName("teacher_rating")
    val teacherRating: String,
    @SerializedName("teacher_status")
    val teacherStatus: Int,
    @SerializedName("teacher_subject")
    val teacherSubject: String,
    @SerializedName("updated_at")
    val updatedAt: String
)