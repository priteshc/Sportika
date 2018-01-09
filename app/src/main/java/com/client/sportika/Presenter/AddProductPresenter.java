package com.client.sportika.Presenter;


import com.client.sportika.Interface.Registerview;
import com.client.sportika.backtask.RetrofitBuild;
import com.client.sportika.model.Registerpojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pritesh on 10/2/2017.
 */

public class AddProductPresenter {


    private Registerview registerview;

    private RetrofitBuild retrofitBuild;


    public AddProductPresenter(Registerview registerview) {

        this.registerview = registerview;

        retrofitBuild = new RetrofitBuild();

    }


    public void addPro(String proname,String proflvr,String probrand,String proweight,String purchseprice,String saleprice ){

        registerview.showprogress();

        Call<Registerpojo> pojoCall = retrofitBuild.allApi().addProduct(proname,probrand,proweight,proflvr,purchseprice,saleprice);


        pojoCall.enqueue(new Callback<Registerpojo>() {
            @Override
            public void onResponse(Call<Registerpojo> call, Response<Registerpojo> response) {

                registerview.hideprogress();


                if(response.body().getSuccess() == 1){


                    registerview.showRLoginSuccessMsg(response.body().getSuccess());

                }
                else {


                    registerview.showRLoginSuccessMsg(response.body().getSuccess());

                }

            }

            @Override
            public void onFailure(Call<Registerpojo> call, Throwable t) {

                registerview.hideprogress();

                registerview.showRErrorMeassage();

            }
        });


    }


}
