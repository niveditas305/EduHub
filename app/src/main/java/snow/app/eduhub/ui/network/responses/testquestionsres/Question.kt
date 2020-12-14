package snow.app.eduhub.ui.network.responses.testquestionsres


import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("answer_five")
    val answerFive: String,
    @SerializedName("answer_four")
    val answerFour: String,
    @SerializedName("answer_one")
    val answerOne: String,
    @SerializedName("answer_three")
    val answerThree: String,
    @SerializedName("answer_two")
    val answerTwo: String,
    @SerializedName("answere_solution")
    val answereSolution: String,
    @SerializedName("chapter_id")
    val chapterId: Int,
    @SerializedName("correct_answere")
    val correctAnswere: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("multiple_question_set_id")
    val multipleQuestionSetId: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("question_number")
    val questionNumber: Int,
    @SerializedName("question_status")
    val questionStatus: String,
    @SerializedName("school_class_id")
    val schoolClassId: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("topic_id")
    val topicId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,


    var isSubmitted: Boolean = false
)