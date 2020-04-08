package flicker.covoid.coronavirus.domain;

import flicker.covoid.coronavirus.model.entities.response.CoronaResponse;
import flicker.covoid.coronavirus.model.rest.CoronaAPIService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CoronaServiceImpl implements CoronaService {

    private CoronaAPIService coronaAPIService;

    public CoronaServiceImpl(CoronaAPIService coronaAPIService) {
        this.coronaAPIService = coronaAPIService;
    }

    @Override
    public Single<CoronaResponse> getCoronaStatus() {
        return coronaAPIService.getApi().getCoronaAPI()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
