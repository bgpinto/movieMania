package challange.kobe.io.moviemania.api

import challange.kobe.io.moviemania.models.UpCommingMoviesResponse
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MDBClient {

    @GET("/3/movie/upcoming")
    fun getUpComingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("api_key") api_key: String = "c5850ed73901b8d268d0898a8a9d8bff"
    ): Observable<UpCommingMoviesResponse>

    companion object {
        private val apiClient by lazy {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build()
            retrofit.create(MDBClient::class.java)
        }

        fun instance(): MDBClient = apiClient
    }
}