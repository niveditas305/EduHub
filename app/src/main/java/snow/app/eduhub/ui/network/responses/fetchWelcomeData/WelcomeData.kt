package snow.app.eduhub.network.responses.fetchWelcomeData

data class WelcomeData(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)