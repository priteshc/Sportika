package com.client.sportika.backtask;

import android.app.Application;
import android.content.Context;

/**
 * Created by yashwant on 6/17/2016.
 */
public class Myapp extends Application {

    private static Myapp instance;

    public static String BASE_URL ="http://173.212.197.93:7100/api/";
    public static final String TAG = Myapp.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


    }

    public static Myapp getInstance()
    {

        return instance;
    }



    public static Context getapContext(){


        return instance.getApplicationContext();
    }



}