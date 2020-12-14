package snow.app.eduhub.network.responses.otpVerification

data class VerifyOTP(
    val `data`: Data,
    val message: String,
    val status: Boolean
)