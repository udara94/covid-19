package flicker.covoid.coronavirus.model.rest;

import flicker.covoid.coronavirus.model.entities.response.CoronaResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CoronaAPI {

    @GET("get-current-statistical")
    Single<CoronaResponse> getCoronaAPI();
}
