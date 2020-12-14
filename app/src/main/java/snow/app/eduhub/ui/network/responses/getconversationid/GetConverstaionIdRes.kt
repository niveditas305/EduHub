package snow.app.eduhub.ui.network.responses.getconversationid


import com.google.gson.annotations.SerializedName

data class GetConverstaionIdRes(
    @SerializedName("conversation_id")
    val conversationId: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)