package snow.app.eduhub.ui.network.responses.scoreres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("average_current_week")
    val averageCurrentWeek: Int,
    @SerializedName("current_attend_test_count")
    val currentAttendTestCount: Int,
    @SerializedName("current_week_test_count")
    val currentWeekTestCount: Int,
    @SerializedName("score_listing")
    val scoreListing: List<ScoreListing>,
    @SerializedName("test_listing")
    val testListing: List<TestListing>
)