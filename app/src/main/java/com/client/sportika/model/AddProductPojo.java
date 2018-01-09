package com.client.sportika.model;

/**
 * Created by HP on 1/8/2018.
 */

public class AddProductPojo {


   private  String proname,probrand,proflvr,proweight,proquantity;

    public AddProductPojo(String proname,String probrand,String proflvr,String proweight,String proquantity) {

        this.proname = proname;
        this.probrand = probrand;
        this.proflvr = proflvr;
        this.proweight = proweight;
        this.proquantity = proquantity;

    }


    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProbrand() {
        return probrand;
    }

    public void setProbrand(String probrand) {
        this.probrand = probrand;
    }

    public String getProflvr() {
        return proflvr;
    }

    public void setProflvr(String proflvr) {
        this.proflvr = proflvr;
    }

    public String getProweight() {
        return proweight;
    }

    public void setProweight(String proweight) {
        this.proweight = proweight;
    }

    public String getProquantity() {
        return proquantity;
    }

    public void setProquantity(String proquantity) {
        this.proquantity = proquantity;
    }
}
