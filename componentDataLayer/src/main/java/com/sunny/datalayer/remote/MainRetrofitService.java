package com.sunny.datalayer.remote;

import com.sunny.datalayer.model.entity.InTheatersEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MainRetrofitService {

    String ENDPOINT = "https://api.douban.com/v2/";

    @GET("movie/in_theaters")
    Observable<InTheatersEntity> getSubjects();

}
