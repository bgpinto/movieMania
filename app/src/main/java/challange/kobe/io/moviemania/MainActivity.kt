package challange.kobe.io.moviemania

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
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
        val title = (movieReciclerView.adapter as MovieArrayAdapter).getItemAt(position).title

        Log.d("___", " Selected title --- $title")

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_stage, MovieDetailsFragment())
            .addToBackStack("MovieDetailsFragment")
            .commit()


    }

    private fun setUpSearchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    movieReciclerView.removeOnScrollListener(mScrollListener)
                    (movieReciclerView.adapter as MovieArrayAdapter).filter(it)
                    if (it.isEmpty()) {
                        movieReciclerView.addOnScrollListener(mScrollListener)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    movieReciclerView.removeOnScrollListener(mScrollListener)
                    (movieReciclerView.adapter as MovieArrayAdapter).filter(it)

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


        var genreMap = HashMap<Long, String>()

        MDBClient.instance().getMovieGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.genres.forEach { it ->
                    genreMap[it.id] = it.name
                }
            }

        MDBClient.instance().getUpComingMovies(mPageCounter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                movieReciclerView.adapter = MovieArrayAdapter(ArrayList(it.results), this, genreMap).apply {
                    setItemClickListener(this@MainActivity)
                }
            }

    }
}
