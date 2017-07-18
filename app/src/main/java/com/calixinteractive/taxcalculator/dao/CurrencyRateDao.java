package com.calixinteractive.taxcalculator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.DatabaseHelper;
import com.calixinteractive.taxcalculator.model.CurrencyRate;
import com.calixinteractive.taxcalculator.schema.CurrencyRateSchema;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maylcf on 12/07/2017.
 */

public class CurrencyRateDao implements CurrencyRateSchema
{
    private Context context;

    public CurrencyRateDao(Context c)
    {
        this.context = c;
    }

    /********************************************************************************************/

    private void insert(CurrencyRate cr)
    {
        // Update Date
        cr.setUpdateDate(new Date());
        ContentValues contentValues = cr.getContentValues();

        DatabaseHelper dao = new DatabaseHelper(context);
        dao.insert(TABLE_NAME, contentValues);
        dao.close();
    }

    private void update(CurrencyRate cr)
    {
        // Update Date
        cr.setUpdateDate(new Date());

        // Populate ID - If necessary
        if (cr.getId() == 0)
            cr.setId(1);

        ContentValues values = cr.getContentValues();
        String[] params = {String.valueOf(cr.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        db.update(TABLE_NAME, values, SELECTION_BY_ID, params);
        db.close();
    }

    public int getCount()
    {
        ArrayList<CurrencyRate> arrayList = selectAll();
        return arrayList.size();
    }

    /********************************************************************************************/

    public void insertOrUpdate(CurrencyRate cr)
    {
        int number = getCount();

        if (number > 0)
        {
            update(cr);
        }
        else
        {
            insert(cr);
        }
    }

    public ArrayList<CurrencyRate> selectAll()
    {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null);

        ArrayList<CurrencyRate> list = new ArrayList<CurrencyRate>();

        while(cursor.moveToNext())
        {
            CurrencyRate cr = new CurrencyRate(cursor);
            list.add(cr);
        }

        cursor.close();
        db.close();

        return list;
    }

    public CurrencyRate getCurrencyRate()
    {
        ArrayList<CurrencyRate> array = selectAll();

        if (!array.isEmpty())
        {
            return array.get(0);
        }
        else
        {
            return null;
        }
    }

    public void insertFirstCurrencyRate()
    {
        CurrencyRate currencyRate = new CurrencyRate();

        currencyRate.setTime(null);
        currencyRate.setCode(null);
        currencyRate.setDate(null);
        currencyRate.setFont(null);
        currencyRate.setUpdateDate(new Date());
        currencyRate.setRate(null);
        currencyRate.setAuto(true);

        insertOrUpdate(currencyRate);
    }

}
