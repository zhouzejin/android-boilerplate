package uk.co.ribot.androidboilerplate.data.remote

import io.reactivex.Observable
import retrofit2.http.GET
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity

interface RetrofitService {

    companion object {
        const val ENDPOINT = "https://api.douban.com/v2/"
    }

    @get:GET("movie/in_theaters")
    val subjects: Observable<InTheatersEntity>

}
