package flicker.covoid.coronavirus.mvp.presenters;

import android.app.Activity;
import android.util.Log;

import flicker.covoid.coronavirus.common.constants.ApplicationConstants;
import flicker.covoid.coronavirus.domain.CoronaService;
import flicker.covoid.coronavirus.domain.Service;
import flicker.covoid.coronavirus.model.entities.response.CoronaResponse;
import flicker.covoid.coronavirus.model.rest.exception.RetrofitException;
import flicker.covoid.coronavirus.mvp.views.CoronaView;
import flicker.covoid.coronavirus.mvp.views.View;
import flicker.covoid.coronavirus.utils.IScheduler;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public class CoronaPresenterImpl extends BasePresenter implements CoronaPresenter {


    private final static String TAG = "JobPresenterImpl";
    private CoronaView mCoronaView;

    public CoronaPresenterImpl(Activity activity, Service mService, IScheduler scheduler) {
        super(activity, mService, scheduler);
    }

    @Override
    public void attachView(View v) {
        if(v instanceof CoronaView){
            mCoronaView = (CoronaView) v;
            mView = mCoronaView;
        }
    }

    @Override
    public void getCoronaDetails() {
        disposable.add(getCoronaObservable().subscribeWith(getCoronaSubscriber()));
    }

    private Single<CoronaResponse> getCoronaObservable(){
        try {
            return getService().getCoronaStatus()
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread());


        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    private DisposableSingleObserver<CoronaResponse> getCoronaSubscriber(){
        return  new DefaultSubscriber<CoronaResponse>(this.mView){

            @Override
            public void onError(Throwable e) {
                try {
                    RetrofitException error = (RetrofitException) e;
                    if(error.getKind() == RetrofitException.Kind.EXPIRED){
                        CoronaResponse exceptionResponse = new CoronaResponse();
                        exceptionResponse.setSuccess(false);
                        exceptionResponse.setTokenExpired(true);
                        exceptionResponse.setMessage(ApplicationConstants.ERROR_MSG_TOKEN_EXPIRED);
                        mCoronaView.showCoronaResponse(exceptionResponse);
                    }else {
                        CoronaResponse response = error.getErrorBodyAs(CoronaResponse.class);
                        if(response == null){
                            response = new CoronaResponse();
                            response.setMessage(getExceptionMessage(e));
                            response.setAPIError(false);
                        }else {
                            response.setAPIError(true);
                        }
                        response.setSuccess(false);
                        mCoronaView.showCoronaResponse(response);
                    }
                }catch (IOException ex){
                    CoronaResponse exceptionResponse = new CoronaResponse();
                    exceptionResponse.setSuccess(false);
                    exceptionResponse.setAPIError(false);
                    exceptionResponse.setMessage(ApplicationConstants.ERROR_MSG_REST_UNEXPECTED);
                    mCoronaView.showCoronaResponse(exceptionResponse);

                    ex.printStackTrace();
                }
                super.onError(e);
            }

            @Override
            public void onSuccess(CoronaResponse response) {
                if(response != null){
                    response.setSuccess(true);
                    mCoronaView.showCoronaResponse(response);
                }
            }
        };
    }

    private CoronaService getService (){
        return (CoronaService) mService;
    }
}
