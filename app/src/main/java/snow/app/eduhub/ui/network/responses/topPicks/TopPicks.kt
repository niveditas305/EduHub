package snow.app.eduhub.network.responses.topPicks

data class TopPicks(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)