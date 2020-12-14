package snow.app.eduhub.ui.network.responses.getsubjectlistbyid


import com.google.gson.annotations.SerializedName

data class GetSubjectListById(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)