package challange.kobe.io.moviemania.adapters

import android.content.Context

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import challange.kobe.io.moviemania.R
import challange.kobe.io.moviemania.models.MovieModel
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mMoviePoster = view.moviewPoster
    val mMovieTitle = view.movieTitle
    val mMovieGenre = view.movieGenre
    val mMovieDate = view.movieDate
}


class MovieArrayAdapter(var mItems: ArrayList<MovieModel>, private val mContext: Context) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val originalList = ArrayList<MovieModel>()

    init {
        originalList.addAll(mItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun filter(key: String) {
        mItems.clear()
        if (key.isEmpty()) {
            mItems.addAll(originalList)
        } else {
            val newItems = ArrayList(originalList.filter { it.title.contains(key, true) })
            mItems.addAll(newItems)

        }

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = mItems[position]
        holder.mMovieTitle.text = item.title
        holder.mMovieGenre.text = formattedGenreFromList(item.genre_ids)
        holder.mMovieDate.text = formattedDateTextFrom(item.release_date)

        setUpMoviePoster(holder.mMoviePoster, item.poster_path)
    }

    private fun setUpMoviePoster(mMoviePoster: ImageView?, posterPath: String) {
        Picasso.get()
            .load("http://image.tmdb.org/t/p/w92/$posterPath")
            .resize(278, 369)
            .centerCrop()
            .into(mMoviePoster)
    }

    private fun formattedDateTextFrom(release_date: String): CharSequence? {
        return "Lançamento: ${release_date.split("-").reversed().joinToString("/")}"
    }

    private fun formattedGenreFromList(genre_ids: List<Int>): CharSequence? {
        // perform back-end search and return a list of genres
        return "Ação"
    }

}
