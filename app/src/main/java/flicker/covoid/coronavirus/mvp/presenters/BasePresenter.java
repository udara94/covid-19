package flicker.covoid.coronavirus.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import flicker.covoid.coronavirus.BaseApplication;
import flicker.covoid.coronavirus.common.constants.ApplicationConstants;
import flicker.covoid.coronavirus.common.constants.IPreferencesKeys;
import flicker.covoid.coronavirus.domain.Service;
import flicker.covoid.coronavirus.model.rest.exception.RetrofitException;
import flicker.covoid.coronavirus.mvp.views.View;
import flicker.covoid.coronavirus.utils.IScheduler;

import io.reactivex.disposables.CompositeDisposable;

public  class BasePresenter implements Presenter {

    protected Activity activity;
    protected Service mService;
    protected CompositeDisposable disposable = new CompositeDisposable();

    protected IScheduler scheduler;
    protected View mView;

    protected SharedPreferences preferences;
    private String access_token;
    private String user_id;

    public BasePresenter(Activity activity, Service mService, IScheduler scheduler) {
        this.activity = activity;
        this.mService = mService;
        this.scheduler = scheduler;
    }

    public String getAccessToken() {
        if (access_token == null || access_token.equals("")) {
            Context mContext = BaseApplication.getBaseApplication();
            SharedPreferences preferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
            String token =  preferences.getString(IPreferencesKeys.ACCESS_TOKEN, "");
            return token;
        } else {
            return  access_token;
        }
    }

    public String getExceptionMessage(Throwable e) {
        if (((RetrofitException) e).getKind() == RetrofitException.Kind.NETWORK) {
            return ApplicationConstants.ERROR_MSG_REST_NETWORK;
        } else if (((RetrofitException) e).getKind() == RetrofitException.Kind.HTTP) {
            return ApplicationConstants.ERROR_MSG_REST_HTTP;
        } else if (((RetrofitException) e).getKind() == RetrofitException.Kind.UNEXPECTED) {
            return ApplicationConstants.ERROR_MSG_REST_UNEXPECTED;
        }
        return null;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void attachView(View v) {

    }

    @Override
    public void onStop() {

    }
}
