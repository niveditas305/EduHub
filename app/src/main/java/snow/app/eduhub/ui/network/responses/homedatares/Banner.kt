package snow.app.eduhub.ui.network.responses.homedatares


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("banner_image")
    val bannerImage: String,
    @SerializedName("banner_text")
    val bannerText: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)