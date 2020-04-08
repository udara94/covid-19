package flicker.covoid.coronavirus.model.rest;

import com.covoid.coronavirus.BuildConfig;
import flicker.covoid.coronavirus.common.constants.DomainConstants;
import flicker.covoid.coronavirus.model.rest.exception.RxErrorHandlingCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoronaAPIService {
    private CoronaAPI coronaAPI;

    public CoronaAPIService() {
        String url = DomainConstants.SERVER_URL;
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
        coronaAPI = restAdapter.create(CoronaAPI.class);
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(); // Log Requests and Responses
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }

        return client.build();
    }

    public CoronaAPI getApi(){
        return coronaAPI;
    }
}
