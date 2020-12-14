package snow.app.eduhub.network.responses.fetchWelcomeData

data class Data(
    val created_at: String,
    val id: Int,
    val page_content: String,
    val page_image: String,
    val page_name: String,
    val updated_at: String
)