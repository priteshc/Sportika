package com.client.sportika.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.client.sportika.R;


/**
 * Created by pritesh on 5/24/2017.
 */

public class Item_Holder extends RecyclerView.ViewHolder {

    public TextView name,brand,flvr,weight,quantity;
    public  LinearLayout continer;


    public Item_Holder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name);
        brand = (TextView) itemView.findViewById(R.id.brand);
        flvr = (TextView) itemView.findViewById(R.id.flvr);
        weight = (TextView) itemView.findViewById(R.id.weight);
        quantity = (TextView) itemView.findViewById(R.id.quant);

      //  continer = itemView.findViewById(R.id.container);

    }
}
