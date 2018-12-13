package challange.kobe.io.moviemania

import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import challange.kobe.io.moviemania.adapters.MovieArrayAdapter
import challange.kobe.io.moviemania.api.MDBClient
import challange.kobe.io.moviemania.models.MovieGenre
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
        setUpSearchViewListener()
    }

    private fun setUpSearchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    (movieReciclerView.adapter as MovieArrayAdapter).filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    (movieReciclerView.adapter as MovieArrayAdapter).filter(it)
                }
                return true
            }
        })
    }

    private fun setUpMovieRecyclerView() {

        movieReciclerView.layoutManager = LinearLayoutManager(this)

        var genreMap = HashMap<Long, String>()

        MDBClient.instance().getMovieGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.genres.forEach { it ->
                    genreMap[it.id] = it.name
                }
            }

        MDBClient.instance().getUpComingMovies(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                movieReciclerView.adapter = MovieArrayAdapter(ArrayList(it.results), this, genreMap)
            }

    }
}
