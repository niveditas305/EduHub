package snow.app.eduhub.ui.network.responses.teachersprofile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("class_name")
    val className: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String,
    @SerializedName("fav")
    val fav: Int,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("reviews_count")
    val reviewsCount: String,
    @SerializedName("reviews_list")
    val reviewsList: List<Reviews>,
    @SerializedName("teacher_address")
    val teacherAddress: String,
    @SerializedName("teacher_class")
    val teacherClass: String,
    @SerializedName("teacher_contact")
    val teacherContact: String,
    @SerializedName("teacher_description")
    val teacherDescription: String,
    @SerializedName("teacher_email_verification")
    val teacherEmailVerification: String,
    @SerializedName("teacher_expirence")
    val teacherExpirence: Int,
    @SerializedName("teacher_first_name")
    val teacherFirstName: String,
    @SerializedName("teacher_id_proof")
    val teacherIdProof: String,
    @SerializedName("teacher_image")
    val teacherImage: String,
    @SerializedName("teacher_last_name")
    val teacherLastName: String,
    @SerializedName("teacher_qualification")
    val teacherQualification: String,
    @SerializedName("teacher_rating")
    val teacherRating: String,
    @SerializedName("teacher_status")
    val teacherStatus: Int,
    @SerializedName("teacher_subject")
    val teacherSubject: String,
    @SerializedName("updated_at")
    val updatedAt: String
)