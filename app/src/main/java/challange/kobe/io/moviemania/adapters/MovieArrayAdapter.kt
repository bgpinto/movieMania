package challange.kobe.io.moviemania.adapters

import android.content.Context

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import challange.kobe.io.moviemania.R
import challange.kobe.io.moviemania.models.MovieGenre
import challange.kobe.io.moviemania.models.MovieModel
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.movie_item_layout.view.*


interface RecyclerViewItemClickListener {
    fun onClick(view: View, position: Int)
}

class MovieViewHolder(view: View, private val mListener: RecyclerViewItemClickListener) : RecyclerView.ViewHolder(view),
    View.OnClickListener {


    override fun onClick(v: View?) {
        mListener.onClick(v!!, adapterPosition)
    }

    val mMoviePoster = view.moviewPoster
    val mMovieTitle = view.movieTitle
    val mMovieGenre = view.movieGenre
    val mMovieDate = view.movieDate

    init {
        view.setOnClickListener(this)
    }

}


class MovieArrayAdapter(
    private var mItems: ArrayList<MovieModel>,
    private val mContext: Context,
    private val mGenres: Map<Long, String>
) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val originalList = ArrayList<MovieModel>()
    private lateinit var mListener: RecyclerViewItemClickListener

    init {
        originalList.addAll(mItems)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.movie_item_layout, parent, false),
            mListener
        )
    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setItemClickListener(listener: RecyclerViewItemClickListener) {
        mListener = listener

    }

    fun getItemAt(position: Int) = mItems[position]

    fun filter(key: String) {
        Log.d("__", "tried key = $key")
        mItems.clear()
        if (key.isEmpty()) {
            mItems.addAll(originalList)
        } else {
            val newItems = ArrayList(originalList.filter { it.title.contains(key, true) })
            mItems.addAll(newItems)

        }

        notifyDataSetChanged()
    }

    fun update(items: List<MovieModel>) {
        originalList.addAll(items)
        mItems.clear()
        mItems.addAll(originalList)
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

        var genreList = ""

        genre_ids.take(3)
            .forEachIndexed { index, it ->
                if (index <= 1) {
                    genreList += mGenres[it.toLong()] + " • "
                } else {
                    genreList += mGenres[it.toLong()]
                }
            }



        return genreList
    }

}
