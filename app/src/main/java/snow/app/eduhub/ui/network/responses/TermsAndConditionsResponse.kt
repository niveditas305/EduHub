package snow.app.eduhub.network.responses

data class TermsAndConditionsResponse(
    val `data`: List<DataX>,
    val message: String,
    val status: Boolean
)