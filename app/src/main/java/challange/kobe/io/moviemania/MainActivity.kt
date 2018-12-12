package challange.kobe.io.moviemania

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


/*
*
* 1. Pegar upcoming movies:
*   - https://developers.themoviedb.org/3/movies/get-upcoming
*
* 2. Pegar imagens:
*   - http://image.tmdb.org/t/p/w185/uXJVpPXxZO4L8Rz3IG1Y8XvZJcg.jpg
*
*
* */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
