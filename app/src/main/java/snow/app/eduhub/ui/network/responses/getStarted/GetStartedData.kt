package snow.app.eduhub.network.responses.getStarted

data class GetStartedData(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)