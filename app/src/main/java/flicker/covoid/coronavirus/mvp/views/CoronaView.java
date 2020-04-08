package flicker.covoid.coronavirus.mvp.views;

import flicker.covoid.coronavirus.model.entities.response.CoronaResponse;

public interface CoronaView extends View {
    void showCoronaResponse(CoronaResponse coronaResponse);
}
