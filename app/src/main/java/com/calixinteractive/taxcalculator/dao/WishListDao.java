package com.calixinteractive.taxcalculator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.DatabaseHelper;
import com.calixinteractive.taxcalculator.model.Exchange;
import com.calixinteractive.taxcalculator.model.WishList;
import com.calixinteractive.taxcalculator.schema.WishListSchema;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maylcf on 14/07/2017.
 */

public class WishListDao implements WishListSchema
{
    private Context context;

    public WishListDao(Context c)
    {
        this.context = c;
    }

    /********************************************************************************************/

    public long insert(WishList wishList)
    {
        wishList.setUpdateDate(new Date());
        ContentValues contentValues = wishList.getContentValues();

        DatabaseHelper db = new DatabaseHelper(context);
        long result = db.insert(TABLE_NAME, contentValues);
        db.close();

        return result;
    }

    public void update(WishList wishList)
    {
        wishList.setUpdateDate(new Date());
        ContentValues values = wishList.getContentValues();
        String[] selectionArgs = {String.valueOf(wishList.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        db.update(TABLE_NAME, values, SELECTION_BY_ID, selectionArgs);
        db.close();
    }

    public void delete(WishList wishList)
    {
        String[] params = {String.valueOf(wishList.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        ExchangeDao exchangeDao = new ExchangeDao(context);

        // Remove Wish List
        db.delete(TABLE_NAME,SELECTION_BY_ID,params);

        // Remove Wish List ID from Exchange
        Exchange exchange = exchangeDao.selectByWishListId(wishList.getId());

        if (exchange != null)
        {
            exchange.setIdWishList(0);
            exchangeDao.insertOrUpdate(exchange);
        }

        db.close();
    }

    public int getCount()
    {
        ArrayList<WishList> array = selectAll();
        return array.size();
    }

    /********************************************************************************************/

    public ArrayList<WishList> selectAll()
    {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null);

        ArrayList<WishList> array = new ArrayList<WishList>();

        while(cursor.moveToNext())
        {
            WishList ws = new WishList(cursor);
            array.add(ws);
        }

        cursor.close();
        db.close();

        return array;
    }

    public WishList selectById(int id)
    {
        String[] selectionArgs = {String.valueOf(id)};

        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, SELECTION_BY_ID, selectionArgs, COLUMN_ID);

        if (cursor.moveToNext())
        {
            WishList p = new WishList(cursor);
            cursor.close();
            db.close();
            return p;
        }
        else
        {
            cursor.close();
            db.close();
            return null;
        }
    }
}
