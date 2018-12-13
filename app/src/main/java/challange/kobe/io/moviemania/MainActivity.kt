package challange.kobe.io.moviemania

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import challange.kobe.io.moviemania.adapters.MovieArrayAdapter
import challange.kobe.io.moviemania.adapters.RecyclerViewItemClickListener
import challange.kobe.io.moviemania.api.MDBClient
import challange.kobe.io.moviemania.details.MovieDetailsFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.atomic.AtomicBoolean


class MainActivity : AppCompatActivity(), RecyclerViewItemClickListener {

    private var mPageCounter = 1
    private val mGenreMap = HashMap<Long, String>()

    private var mScrollListener = object : RecyclerView.OnScrollListener() {

        val isLoading = AtomicBoolean(false)

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
            val visibleItemsCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()


            if (isLoading.get().not() && visibleItemsCount <= (lastVisibleItem + (5 * mPageCounter))) {

                isLoading.set(true)
                mPageCounter++
                MDBClient.instance().getUpComingMovies(mPageCounter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        (movieReciclerView.adapter as MovieArrayAdapter).update(it.results)
                        isLoading.set(false)
                    }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMovieRecyclerView()
        setUpSearchViewListener()
    }


    override fun onClick(view: View, position: Int) {
        val model = (movieReciclerView.adapter as MovieArrayAdapter).getItemAt(position)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_stage, MovieDetailsFragment.newInstance(Bundle().apply {
                putString("title", model.title)
                putString("genre", formattedGenreFromList(model.genre_ids))
                putString("date", formattedDateTextFrom(model.release_date))
                putString("overview", model.overview)
                putString("backdrop", model.backdrop_path)
            }))
            .addToBackStack("MovieDetailsFragment")
            .commit()


    }

    private fun formattedDateTextFrom(release_date: String): String {
        return "Lançamento: ${release_date.split("-").reversed().joinToString("/")}"
    }

    private fun formattedGenreFromList(genre_ids: List<Int>): String {
        var genreList = ""

        genre_ids.take(3)
            .forEachIndexed { index, it ->
                if (index <= 1) {
                    genreList += mGenreMap[it.toLong()] + " • "
                } else {
                    genreList += mGenreMap[it.toLong()]
                }
            }



        return genreList
    }

    private fun setUpSearchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    movieReciclerView.removeOnScrollListener(mScrollListener)
                    (movieReciclerView.adapter as? MovieArrayAdapter)?.filter(it)
                    if (it.isEmpty()) {
                        movieReciclerView.addOnScrollListener(mScrollListener)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    movieReciclerView.removeOnScrollListener(mScrollListener)
                    (movieReciclerView.adapter as? MovieArrayAdapter)?.filter(it)

                    if (it.isEmpty()) {
                        movieReciclerView.addOnScrollListener(mScrollListener)
                    }
                }
                return true
            }
        })
    }

    private fun setUpMovieRecyclerView() {

        movieReciclerView.layoutManager = LinearLayoutManager(this)
        movieReciclerView.itemAnimator = DefaultItemAnimator()
        movieReciclerView.addOnScrollListener(mScrollListener)


        MDBClient.instance().getMovieGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.genres.forEach { it ->
                    mGenreMap[it.id] = it.name
                }
            }

        MDBClient.instance().getUpComingMovies(mPageCounter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                movieReciclerView.adapter = MovieArrayAdapter(ArrayList(it.results), this, mGenreMap).apply {
                    setItemClickListener(this@MainActivity)
                }
            }

    }


}
