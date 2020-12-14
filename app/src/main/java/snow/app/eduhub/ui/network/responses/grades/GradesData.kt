package snow.app.eduhub.network.responses.grades

data class GradesData(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)