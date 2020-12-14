package snow.app.eduhub.network.responses.login

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val token: String
)