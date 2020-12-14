package snow.app.eduhub.ui.network.responses.testlistres


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("current_week_test")
    val currentWeekTest: List<CurrentWeekTest>,
    @SerializedName("old_week_test")
    val oldWeekTest: List<OldWeekTest>
)