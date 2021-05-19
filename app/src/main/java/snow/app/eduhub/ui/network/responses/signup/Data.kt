package snow.app.eduhub.ui.network.responses.signup


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("device_token")
    var deviceToken: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("facebook_id")
    var facebookId: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("google_id")
    var googleId: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("mobile_otp")
    var mobileOtp: Int,
    @SerializedName("notification_setup")
    var notificationSetup: Int,
    @SerializedName("password")
    var password: String,
    @SerializedName("school_class_id")
    var schoolClassId: Int,
    @SerializedName("school_class_name")
    var schoolClassName: String,
    @SerializedName("student_address")
    var studentAddress: String,
    @SerializedName("student_full_name")
    var studentFullName: String,
    @SerializedName("student_image")
    var studentImage: String,
    @SerializedName("student_moblie")
    var studentMoblie: String,

    @SerializedName("country_code")
    var country_code: String,
    @SerializedName("student_school")
    var studentSchool: String,
    @SerializedName("student_status")
    var studentStatus: Int,
    @SerializedName("token")
    var token: String,
    @SerializedName("updated_at")
    var updatedAt: String
)