package snow.app.eduhub.ui.network.responses.fetchprofile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("facebook_id")
    val facebookId: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("google_id")
    val googleId: String,
    @SerializedName("grade")
    val grade: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("mobile_otp")
    val mobileOtp: Int,
    @SerializedName("notification_setup")
    val notificationSetup: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("student_address")
    val studentAddress: String,
    @SerializedName("student_full_name")
    val studentFullName: String,
    @SerializedName("student_image")
    val studentImage: String,
    @SerializedName("student_moblie")
    val studentMoblie: String,

    @SerializedName("country_code")
    val country_code: String,
    @SerializedName("student_school")
    val studentSchool: String,
    @SerializedName("student_status")
    val studentStatus: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)