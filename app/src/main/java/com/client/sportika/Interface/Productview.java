package com.client.sportika.Interface;


import com.client.sportika.model.Product;

import java.util.List;

/**
 * Created by pritesh on 10/2/2017.
 */

public interface Productview {


    void showPErrorMeassage();

    void showPLoginSuccessMsg(List<Product> PList);

    void showprogress();

    void hideprogress();


}
