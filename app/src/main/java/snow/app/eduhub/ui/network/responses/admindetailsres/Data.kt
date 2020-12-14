package snow.app.eduhub.ui.network.responses.admindetailsres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("admin_image")
    val adminImage: Any,
    @SerializedName("contact_time")
    val contactTime: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("remember_token")
    val rememberToken: Any,
    @SerializedName("updated_at")
    val updatedAt: String
)