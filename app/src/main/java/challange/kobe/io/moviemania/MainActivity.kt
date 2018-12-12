package challange.kobe.io.moviemania

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import challange.kobe.io.moviemania.adapters.MovieArrayAdapter
import challange.kobe.io.moviemania.api.MDBClient
import challange.kobe.io.moviemania.models.MovieModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

/*
*
* 1. Pegar upcoming movies:
*   - https://developers.themoviedb.org/3/movies/get-upcoming
*
* 2. Pegar imagens:
*   - http://image.tmdb.org/t/p/w92/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg
*
*
* */

class MainActivity : AppCompatActivity() {

    private val mMovieArray by lazy {
        ArrayList<MovieModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMovieRecyclerView()
    }

    private fun setUpSearchViewListener() {
        TODO("NEED TO SET IN ORDER TO SEARCH OTHERWISE THINGS ARE GONNA BLOW")
    }

    private fun setUpMovieRecyclerView() {

        /*
        mMovieArray.add(MovieModel().apply {
            vote_count = 58
            id = 428078
            video = false
            vote_average = 5.9f
            title = "Mortal Engines"
            popularity = 181.253f
            poster_path = "/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg"
            original_language = "en"
            original_title = "Mortal Engines"
            genre_ids = arrayListOf(878)
            backdrop_path = "\\xYG6Sj95as9rv9wKIHUx6ATWd3.jpg"
            adult = false
            overview =
                    "Set in a world many thousands of years in the future. Earth’s cities now roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive Traction Cities, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the Outlands who will change the course of his life forever."
            release_date = "2018-12-06"
        })



        mMovieArray.add(MovieModel().apply {
            vote_count = 58
            id = 428078
            video = false
            vote_average = 5.9f
            title = "Mortal Engines"
            popularity = 181.253f
            poster_path = "/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg"
            original_language = "en"
            original_title = "Mortal Engines"
            genre_ids = arrayListOf(878)
            backdrop_path = "\\xYG6Sj95as9rv9wKIHUx6ATWd3.jpg"
            adult = false
            overview =
                    "Set in a world many thousands of years in the future. Earth’s cities now roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive Traction Cities, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the Outlands who will change the course of his life forever."
            release_date = "2018-12-06"
        })


        mMovieArray.add(MovieModel().apply {
            vote_count = 58
            id = 428078
            video = false
            vote_average = 5.9f
            title = "Mortal Engines"
            popularity = 181.253f
            poster_path = "/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg"
            original_language = "en"
            original_title = "Mortal Engines"
            genre_ids = arrayListOf(878)
            backdrop_path = "\\xYG6Sj95as9rv9wKIHUx6ATWd3.jpg"
            adult = false
            overview =
                    "Set in a world many thousands of years in the future. Earth’s cities now roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive Traction Cities, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the Outlands who will change the course of his life forever."
            release_date = "2018-12-06"
        })


        mMovieArray.add(MovieModel().apply {
            vote_count = 58
            id = 428078
            video = false
            vote_average = 5.9f
            title = "Mortal Engines"
            popularity = 181.253f
            poster_path = "/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg"
            original_language = "en"
            original_title = "Mortal Engines"
            genre_ids = arrayListOf(878)
            backdrop_path = "\\xYG6Sj95as9rv9wKIHUx6ATWd3.jpg"
            adult = false
            overview =
                    "Set in a world many thousands of years in the future. Earth’s cities now roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive Traction Cities, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the Outlands who will change the course of his life forever."
            release_date = "2018-12-06"
        })


        mMovieArray.add(MovieModel().apply {
            vote_count = 58
            id = 428078
            video = false
            vote_average = 5.9f
            title = "Mortal Engines"
            popularity = 181.253f
            poster_path = "/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg"
            original_language = "en"
            original_title = "Mortal Engines"
            genre_ids = arrayListOf(878)
            backdrop_path = "\\xYG6Sj95as9rv9wKIHUx6ATWd3.jpg"
            adult = false
            overview =
                    "Set in a world many thousands of years in the future. Earth’s cities now roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive Traction Cities, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the Outlands who will change the course of his life forever."
            release_date = "2018-12-06"
        })


        mMovieArray.add(MovieModel().apply {
            vote_count = 58
            id = 428078
            video = false
            vote_average = 5.9f
            title = "Mortal Engines"
            popularity = 181.253f
            poster_path = "/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg"
            original_language = "en"
            original_title = "Mortal Engines"
            genre_ids = arrayListOf(878)
            backdrop_path = "\\xYG6Sj95as9rv9wKIHUx6ATWd3.jpg"
            adult = false
            overview =
                    "Set in a world many thousands of years in the future. Earth’s cities now roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive Traction Cities, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the Outlands who will change the course of his life forever."
            release_date = "2018-12-06"
        })*/
        movieReciclerView.layoutManager = LinearLayoutManager(this)

        MDBClient.instance().getUpComingMovies(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                movieReciclerView.adapter = MovieArrayAdapter(ArrayList<MovieModel>(it.results), this)
            }

    }
}
