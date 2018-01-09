package com.client.sportika.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.client.sportika.R;
import com.client.sportika.holder.Item_Holder;
import com.client.sportika.model.AddProductPojo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pritesh on 5/24/2017.
 */

public class Item_Adapter extends RecyclerView.Adapter<Item_Holder> {

    Context mContext;

    private  ArrayList<AddProductPojo>productPojos ;

    private int lastPosition = -1;



    public Item_Adapter(Context c, ArrayList<AddProductPojo>productPojos ) {

        mContext = c;
        this.productPojos = productPojos;


    }

    @Override
    public Item_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_item_card, parent, false);

        return new Item_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Item_Holder holder, final int position) {


        holder.name.setText(productPojos.get(position).getProname());
        holder.brand.setText(productPojos.get(position).getProbrand());
        holder.flvr.setText(productPojos.get(position).getProflvr());
        holder.weight.setText(productPojos.get(position).getProweight());
        holder.quantity.setText(productPojos.get(position).getProquantity());




    }




    @Override
    public int getItemCount() {
        return productPojos.size();
    }
}
