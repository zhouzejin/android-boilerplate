package uk.co.ribot.androidboilerplate.data.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity;

public interface RetrofitService {

    String ENDPOINT = "https://douban.uieee.com/v2/";

    @GET("movie/in_theaters")
    Observable<InTheatersEntity> getSubjects();

}
