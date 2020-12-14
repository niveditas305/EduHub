package snow.app.eduhub.ui.network.responses.searchres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("chapter")
    val chapter: List<Chapter>,
    @SerializedName("subject_data")
    val subjectData: List<SubjectData>
)