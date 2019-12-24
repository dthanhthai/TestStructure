package vn.gogo.trip.data.response.google_video

data class GoogleVideoResponse(
    var googlevideos:List<Googlevideo> = listOf()
)

data class Googlevideo(
    var category: String = "",
    var videos: List<Video> = listOf()
)

data class Video(
    var description: String = "",
    var sources: List<String> = listOf(),
    var card: String = "",
    var background: String = "",
    var title: String = "",
    var studio: String = ""
)