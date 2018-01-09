package com.client.sportika.backtask;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pritesh on 3/27/2017.
 */

public class RetrofitBuild  {

    public RetrofitBuild() {

    }

    public AllApi allApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myapp.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(AllApi.class);
    }

}
