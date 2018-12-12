package challange.kobe.io.moviemania.adapters

import android.content.Context

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import challange.kobe.io.moviemania.R
import challange.kobe.io.moviemania.models.MovieModel

import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mMoviePoster = view.moviewPoster
    val mMovieTitle = view.movieTitle
    val mMovieGenre = view.movieGenre
    val mMovieDate = view.movieDate
}


class MovieArrayAdapter(val mItems: ArrayList<MovieModel>, private val mContext: Context) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = mItems[position]
        holder.mMovieTitle.text = item.title
        holder.mMovieGenre.text = formattedGenreFromList(item.genre_ids)
        holder.mMovieDate.text =  formattedDateTextFrom(item.release_date)

        //holder.mMoviePoster setar com picasso or placeholder
    }

    private fun formattedDateTextFrom(release_date: String): CharSequence? {
        return "Lançamento: ${release_date.split("-").reversed().joinToString("/")}"
    }

    private fun formattedGenreFromList(genre_ids: List<Int>): CharSequence? {
        // perform back-end search and return a list of genres
        return "Ação"
    }

}
