package uk.co.ribot.androidboilerplate.data.remote;

import retrofit2.http.GET;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity;

public interface RetrofitService {

    String ENDPOINT = "https://api.douban.com/v2/";

    @GET("movie/in_theaters")
    Observable<InTheatersEntity> getSubjects();

}
