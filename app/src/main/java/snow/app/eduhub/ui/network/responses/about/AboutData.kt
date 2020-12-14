package snow.app.eduhub.network.responses.about

data class AboutData(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)