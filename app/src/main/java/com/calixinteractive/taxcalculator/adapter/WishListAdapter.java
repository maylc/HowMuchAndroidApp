package com.calixinteractive.taxcalculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.model.WishList;

import java.util.ArrayList;

/**
 * Created by maylcf on 13/07/2017.
 */

public class WishListAdapter extends ArrayAdapter<WishList>
{
    private Context context;
    private ArrayList<WishList> list;

    public WishListAdapter(@NonNull Context context, ArrayList<WishList> list)
    {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        /************************************/
        /** Get Data from current Position***/
        /************************************/

        WishList ws = list.get(position);

        /************************************/
        /********** Check Context ***********/
        /************************************/

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_wishlist_layout, parent, false);
        }

        /***********************/
        /**** Get Objects ******/
        /***********************/

        TextView itemId        = (TextView) convertView.findViewById(R.id.adapter_wishlist_id);
        TextView itemTitle     = (TextView) convertView.findViewById(R.id.adapter_wishlist_product);
        TextView itemStore     = (TextView) convertView.findViewById(R.id.adapter_wishlist_store);
        TextView itemPrice     = (TextView) convertView.findViewById(R.id.adapter_wishlist_price);

        /****************************/
        /**** Prepare the Content ***/
        /****************************/

        String id     = String.valueOf(ws.getId());
        String title  = ws.getName();
        String store  = ws.getStore();
        String dprice = ws.getInitPriceToScreen();
        String cPrice = ws.getConvertedPriceToScreen();

        String price = "Dollar: " + dprice + " | " + "Real: " + cPrice;


        /***********************/
        /**** Build the List ***/
        /***********************/

        itemId.setText(id);
        itemTitle.setText(title);
        itemStore.setText(store);
        itemPrice.setText(price);

        /***********************/
        /***** Return **********/
        /***********************/

        return convertView;
    }

}
