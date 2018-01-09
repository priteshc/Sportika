package com.client.sportika.backtask;


import com.client.sportika.model.Productpojo;
import com.client.sportika.model.Registerpojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by pritesh on 3/27/2017.
 */

public interface AllApi {


    @FormUrlEncoded
    @POST("registeration")
    Call<Registerpojo> getRegister(@Field("business_name") String name,@Field("phone_no") String number,@Field("email_id") String eid);

    @FormUrlEncoded
    @POST("add_product")
    Call<Registerpojo> addProduct(@Field("item_name") String item,@Field("brand_name") String brand,@Field("item_weight") String weight,@Field("item_flvr") String flvr,@Field("item_purchase_price") String purchase,@Field("item_sale_price") String sale);


    @GET("product_details")
    Call<Productpojo> getProduct();




}
