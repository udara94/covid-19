package flicker.covoid.coronavirus.mvp.presenters;

import flicker.covoid.coronavirus.mvp.views.View;

public interface Presenter {
    void onCreate();
    void onStart();
    void onDestroy();
    void attachView(View v);
    void onStop();
}
