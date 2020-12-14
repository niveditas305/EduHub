package snow.app.eduhub.network.responses.notification

data class SetNotificationStatus(
    val `data`: Data,
    val message: String,
    val status: Boolean
)