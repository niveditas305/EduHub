package snow.app.eduhub.network.responses

data class PrivacyPolicyResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)