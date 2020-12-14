package snow.app.eduhub.ui.network.responses.pastquestions


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("past_question_category_id")
    val pastQuestionCategoryId: Int,
    @SerializedName("question_description")
    val questionDescription: String,
    @SerializedName("question_name")
    val questionName: String,
    @SerializedName("question_pdf")
    val questionPdf: String,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("class_name")
    val class_name: String
)