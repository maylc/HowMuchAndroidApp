package com.calixinteractive.taxcalculator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.DatabaseHelper;
import com.calixinteractive.taxcalculator.model.Exchange;
import com.calixinteractive.taxcalculator.schema.ExchangeSchema;

import java.util.ArrayList;
import java.util.Date;

public class ExchangeDao implements ExchangeSchema
{
    private Context context;

    public ExchangeDao(Context c)
    {
        this.context = c;
    }

    /********************************************************************************************/

    private int insert(Exchange exchange)
    {
        exchange.setUpdateDate(new Date());
        ContentValues cv = exchange.getContentValues();

        DatabaseHelper db = new DatabaseHelper(context);
        long result = db.insert(TABLE_NAME, cv);
        db.close();
        return (int) result;
    }

    private void update(Exchange exchange)
    {
        exchange.setUpdateDate(new Date());
        ContentValues values = exchange.getContentValues();
        String[] selectionArgs = {String.valueOf(exchange.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        db.update(TABLE_NAME, values, SELECTION_BY_ID, selectionArgs);
        db.close();
    }

    public void remove(Exchange exchange)
    {
        ContentValues cv = exchange.getContentValues();
        String[] params = {String.valueOf(exchange.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        int result = db.delete(TABLE_NAME, SELECTION_BY_ID, params);
        db.close();
    }

    public int getCount()
    {
        ArrayList<Exchange> array = selectAll();
        return array.size();
    }

    /********************************************************************************************/

    public void insertOrUpdate(Exchange exchange)
    {
        int number = getCount();

        if (number > 0)
        {
            update(exchange);
        }
        else {
            insert(exchange);
        }
    }

    public Exchange selectExchange()
    {
        ArrayList<Exchange> array = selectAll();

        if (!array.isEmpty())
        {
            return array.get(0);
        }
        else
        {
            return null;
        }
    }

    public ArrayList<Exchange> selectAll()
    {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null);

        ArrayList<Exchange> array = new ArrayList<Exchange>();

        while(cursor.moveToNext())
        {
            Exchange e = new Exchange(cursor);
            array.add(e);
        }

        cursor.close();
        db.close();

        return array;
    }

    public Exchange selectByWishListId(int wishListId)
    {
        String[] selectionArgs = {String.valueOf(wishListId)};
        Exchange exchange = null;

        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, SELECTION_BY_WISHLIST_ID, selectionArgs, null);

        while(cursor.moveToNext())
        {
            exchange = new Exchange(cursor);
        }

        return exchange;
    }

}
