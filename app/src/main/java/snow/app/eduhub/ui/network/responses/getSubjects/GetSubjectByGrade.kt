package snow.app.eduhub.network.responses.getSubjects

data class GetSubjectByGrade(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)