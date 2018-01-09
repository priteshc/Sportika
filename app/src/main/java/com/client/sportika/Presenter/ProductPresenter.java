package com.client.sportika.Presenter;


import com.client.sportika.Interface.Productview;
import com.client.sportika.Interface.Registerview;
import com.client.sportika.backtask.RetrofitBuild;
import com.client.sportika.model.Productpojo;
import com.client.sportika.model.Registerpojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pritesh on 10/2/2017.
 */

public class ProductPresenter {


    private Productview productview;

    private RetrofitBuild retrofitBuild;


    public ProductPresenter(Productview productview) {

        this.productview = productview;

        retrofitBuild = new RetrofitBuild();

    }


    public void getProduct(){

        productview.showprogress();

        Call<Productpojo> pojoCall = retrofitBuild.allApi().getProduct();


        pojoCall.enqueue(new Callback<Productpojo>() {
            @Override
            public void onResponse(Call<Productpojo> call, Response<Productpojo> response) {

                productview.hideprogress();


                if(response.body().getSuccess() == "1"){


                    productview.showPLoginSuccessMsg(response.body().getData());

                }
                else {


                    productview.showPLoginSuccessMsg(response.body().getData());

                }

            }

            @Override
            public void onFailure(Call<Productpojo> call, Throwable t) {

                productview.hideprogress();

                productview.showPErrorMeassage();

            }
        });


    }


}
