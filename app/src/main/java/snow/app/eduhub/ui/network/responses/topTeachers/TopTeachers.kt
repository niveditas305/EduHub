package snow.app.eduhub.network.responses.topTeachers

data class TopTeachers(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)