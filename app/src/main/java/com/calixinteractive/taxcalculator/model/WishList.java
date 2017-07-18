package com.calixinteractive.taxcalculator.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.GeneralFunctions;
import com.calixinteractive.taxcalculator.schema.WishListSchema;

import java.io.Serializable;

/**
 * Created by maylcf on 14/07/2017.
 */

public class WishList extends Exchange implements Serializable
{
    private String name;
    private String store;


    public WishList ()
    {

    }

    public WishList(Cursor c)
    {
        this.name   = c.getString(c.getColumnIndex(WishListSchema.COLUMN_NAME));
        this.store  = c.getString(c.getColumnIndex(WishListSchema.COLUMN_STORE));

        this.setId(c.getInt(c.getColumnIndex(WishListSchema.COLUMN_ID)));
        this.setInitPrice(c.getFloat(c.getColumnIndex(WishListSchema.COLUMN_INIT_PRICE)));
        this.setTotalTaxes(c.getFloat(c.getColumnIndex(WishListSchema.COLUMN_TOTAL_TAXES)));
        this.setCurrencyRate(c.getFloat(c.getColumnIndex(WishListSchema.COLUMN_CURRENCY)));
        this.setUpdateDate(GeneralFunctions.convertStringToDateTime(c.getString(c.getColumnIndex(WishListSchema.COLUMN_DATE))));
    }

    /***************************************************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    /***************************************************************/

    public ContentValues getContentValues()
    {
        ContentValues dados = new ContentValues();

        dados.put(WishListSchema.COLUMN_NAME        , this.name);
        dados.put(WishListSchema.COLUMN_STORE       , this.store);
        dados.put(WishListSchema.COLUMN_INIT_PRICE  , getInitPrice());
        dados.put(WishListSchema.COLUMN_CURRENCY    , getCurrencyRate());
        dados.put(WishListSchema.COLUMN_TOTAL_TAXES , getTotalTaxes());
        dados.put(WishListSchema.COLUMN_DATE        ,  GeneralFunctions.convertDateTimeToString(getUpdateDate()));

        return dados;
    }

}
