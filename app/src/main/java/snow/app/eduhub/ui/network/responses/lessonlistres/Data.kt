package snow.app.eduhub.ui.network.responses.lessonlistres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("chapter_id")
    var chapterId: Int,
    @SerializedName("chapter_name")
    var chapterName: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("lesson_description")
    var lessonDescription: String,
    @SerializedName("lesson_name")
    var lessonName: String,
    @SerializedName("lesson_pdf")
    var lessonPdf: String,
    @SerializedName("school_class_id")
    var schoolClassId: Int,
    @SerializedName("status")
    var status: Int,
    @SerializedName("subject_id")
    var subjectId: Int,
    @SerializedName("subject_name")
    var subjectName: String,
    @SerializedName("teacher_id")
    var teacherId: Int,
    @SerializedName("teacher_name")
    var teacherName: String,
    @SerializedName("updated_at")
    var updatedAt: String
)