package challange.kobe.io.moviemania.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import challange.kobe.io.moviemania.R
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.movie_details_layout.*

class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle) = MovieDetailsFragment().apply {
            arguments = bundle
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.run {
            movieTitle.text = arguments?.getString("title")
            movieGenre.text = arguments?.getString("genre")
            movieDate.text = arguments?.getString("date")
            movieOverview.text = arguments?.getString("overview")

            Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/${arguments?.getString("backdrop")}")
                .resize(1024, 420)
                .centerCrop()
                .into(moviewBackDrop)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_details_layout, container, false)
    }
}