package com.calixinteractive.taxcalculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.model.Province;

import java.util.ArrayList;

/**
 * Created by maylcf on 13/07/2017.
 */

public class ProvinceAdapter extends ArrayAdapter<Province>
{
    private Context context;
    private ArrayList<Province> list;

    public ProvinceAdapter(@NonNull Context context, ArrayList<Province> list)
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

        Province p = list.get(position);

        /************************************/
        /********** Check Context ***********/
        /************************************/

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_list_layout, parent, false);
        }

        /***********************/
        /**** Get Objects ******/
        /***********************/

        TextView itemTitle = (TextView) convertView.findViewById(R.id.adapter_list_title);
        TextView itemId    = (TextView) convertView.findViewById(R.id.adapter_list_id);

        /****************************/
        /**** Prepare the Content ***/
        /****************************/

        String title = p.getName() + " - " + p.getCode();
        String id    = String.valueOf(p.getId());

        /***********************/
        /**** Build the List ***/
        /***********************/

        itemTitle.setText(title);
        itemId.setText(id);

        /***********************/
        /***** Return **********/
        /***********************/

        return convertView;
    }
}
