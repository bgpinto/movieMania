package challange.kobe.io.moviemania.models

data class MovieModel(
    var vote_count: Long = 0,
    var id: Long = 0,
    var video: Boolean = false,
    var vote_average: Float = 0.0f,
    var title: String = "",
    var popularity: Float = 0.0f,
    var poster_path: String = "",
    var original_language: String = "",
    var original_title: String = "",
    var genre_ids: List<Int> = emptyList(),
    var backdrop_path: String = "",
    var adult: Boolean = false,
    var overview: String = "",
    var release_date: String = ""
)