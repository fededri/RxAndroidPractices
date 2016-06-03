package com.rxandroidpractice.connections;



import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Federico Torres on 18/3/2016.
 */
public class ApiClient {

    private static String API_URL = "http://care.xanthops.com/api/v1";

    private static Care4ApiClientInfinixInterface care4ApiClientInterface;

    public static Care4ApiClientInfinixInterface getServiceClient() {
        if (care4ApiClientInterface == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestInterceptor.RequestFacade request) {

                        }
                    })
                    .setEndpoint(API_URL)
                   // .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("Retrofit"))
                    .build();

            care4ApiClientInterface = restAdapter.create(Care4ApiClientInfinixInterface.class);
        }

        return care4ApiClientInterface;
    }


    public interface  Care4ApiClientInfinixInterface{


        @GET("/pathology")
        void getPathologies(Callback<List<Pathology>> callback);



        @GET("/pathology")
        Observable<List<Pathology>>  getPathologiesRx();

    }

}
