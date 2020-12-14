package snow.app.eduhub.network.responses.homeBanner

data class Data(
    val banner_image: String,
    val banner_text: String,
    val created_at: String,
    val id: Int,
    val status: String,
    val updated_at: String
)