package snow.app.eduhub.ui.network.responses.getstudyguild


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("class_name")
    val className: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pdf_description")
    val pdfDescription: String,
    @SerializedName("pdf_icon")
    val pdfIcon: String,
    @SerializedName("pdf_title")
    val pdfTitle: String,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("study_pdf")
    val studyPdf: String,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_description")
    val videoDescription: String,
    @SerializedName("video_name")
    val videoName: String,
    @SerializedName("video_path")
    val videoPath: String,
    @SerializedName("video_thumbnail")
    val videoThumbnail: String,
    @SerializedName("video_type")
    val videoType: String
)