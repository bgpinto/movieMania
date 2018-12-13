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

data class DateRange(
    var maximum: String,
    var minimum: String
)

data class UpCommingMoviesResponse(
    var results: List<MovieModel>,
    var page: Int,
    var total_results: Int,
    var total_pages: Int,
    var dates: DateRange
)


data class MovieGenresResponse(
    var genres: List<MovieGenre>
)

data class MovieGenre(
    var id: Long = 0,
    var name: String
)
