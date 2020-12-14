package snow.app.eduhub.network.responses.homeBanner

data class HomeBannerData(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)