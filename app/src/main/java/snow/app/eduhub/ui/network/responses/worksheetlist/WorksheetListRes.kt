package snow.app.eduhub.ui.network.responses.worksheetlist


import com.google.gson.annotations.SerializedName

data class WorksheetListRes(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean
)