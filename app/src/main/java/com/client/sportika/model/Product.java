package com.client.sportika.model;

/**
 * Created by HP on 1/5/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("item_weight")
    @Expose
    private String itemWeight;
    @SerializedName("item_flvr")
    @Expose
    private String itemFlvr;
    @SerializedName("item_purchase_price")
    @Expose
    private String itemPurchasePrice;
    @SerializedName("item_sale_price")
    @Expose
    private String itemSalePrice;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemFlvr() {
        return itemFlvr;
    }

    public void setItemFlvr(String itemFlvr) {
        this.itemFlvr = itemFlvr;
    }

    public String getItemPurchasePrice() {
        return itemPurchasePrice;
    }

    public void setItemPurchasePrice(String itemPurchasePrice) {
        this.itemPurchasePrice = itemPurchasePrice;
    }

    public String getItemSalePrice() {
        return itemSalePrice;
    }

    public void setItemSalePrice(String itemSalePrice) {
        this.itemSalePrice = itemSalePrice;
    }

}
