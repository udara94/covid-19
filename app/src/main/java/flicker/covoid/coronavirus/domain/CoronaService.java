package flicker.covoid.coronavirus.domain;

import flicker.covoid.coronavirus.model.entities.response.CoronaResponse;

import io.reactivex.Single;

public interface CoronaService extends Service {
    Single<CoronaResponse> getCoronaStatus();
}
