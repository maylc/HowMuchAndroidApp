package com.calixinteractive.taxcalculator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.DatabaseHelper;
import com.calixinteractive.taxcalculator.model.Province;
import com.calixinteractive.taxcalculator.schema.ProvinceSchema;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maylcf on 12/07/2017.
 */

public class ProvincesDao implements ProvinceSchema
{
    private Context context;

    public ProvincesDao(Context c)
    {
        this.context = c;
    }

    /********************************************************************************************/

    private void insert(Province province)
    {
        province.setUpdateDate(new Date());
        ContentValues contentValues = province.getContentValues();

        DatabaseHelper db = new DatabaseHelper(context);
        db.insert(TABLE_NAME, contentValues);
        db.close();
    }

    private void update(Province province)
    {
        province.setUpdateDate(new Date());
        ContentValues values = province.getContentValues();
        String[] selectionArgs = {String.valueOf(province.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        db.update(TABLE_NAME, values, SELECTION_BY_ID, selectionArgs);
        db.close();
    }

    private void delete(Province province)
    {
        String[] params = {String.valueOf(province.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        db.delete(TABLE_NAME,SELECTION_BY_ID,params);
        db.close();
    }

    public int getCount()
    {
        ArrayList<Province> arrayOfProvinces = selectAll();
        return arrayOfProvinces.size();
    }

    /********************************************************************************************/

    public void insertOrUpdate(Province province)
    {
        Province provinceCheck = selectByCode(province.getCode());

        if (provinceCheck != null)
        {
            update(province);
        }
        else
        {
            insert(province);
        }
    }

    /********************************************************************************************/

    public Province selectByCode(String provinceCode)
    {
        String[] params = {provinceCode};
        Province province = null;

        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, SELECTION_BY_CODE, params, COLUMN_CODE);

        if (cursor.moveToNext())
        {
            province = new Province(cursor);
        }

        cursor.close();
        db.close();
        return province;

    }

    public ArrayList<Province> selectAll()
    {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null);

        ArrayList<Province> list = new ArrayList<Province>();

        while(cursor.moveToNext())
        {
            Province p = new Province(cursor);
            list.add(p);
        }

        cursor.close();
        db.close();

        return list;
    }
}
