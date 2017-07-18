package com.calixinteractive.taxcalculator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.helper.DatabaseHelper;
import com.calixinteractive.taxcalculator.model.Preferences;
import com.calixinteractive.taxcalculator.schema.PreferencesSchema;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maylcf on 13/07/2017.
 */

public class PreferencesDao implements PreferencesSchema
{
    private Context context;

    public PreferencesDao(Context c)
    {
        this.context = c;
    }

    /********************************************************************************************/

    private int insert(Preferences preferences)
    {
        preferences.setUpdateDate(new Date());
        ContentValues contentValues = preferences.getContentValues();

        DatabaseHelper db = new DatabaseHelper(context);
        int result = db.insert(TABLE_NAME, contentValues);
        db.close();

        return result;
    }

    private int update(Preferences preferences)
    {
        preferences.setUpdateDate(new Date());

        if (preferences.getId() == 0)
            preferences.setId(1);

        ContentValues values = preferences.getContentValues();
        String[] selectionArgs = {String.valueOf(preferences.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        int result = db.update(TABLE_NAME, values, SELECTION_BY_ID, selectionArgs);
        db.close();

        return result;
    }

    public void delete(Preferences preferences)
    {
        String[] params = {String.valueOf(preferences.getId())};

        DatabaseHelper db = new DatabaseHelper(context);
        db.delete(TABLE_NAME,SELECTION_BY_ID,params);
        db.close();
    }

    public int getCount()
    {
        ArrayList<Preferences> array = selectAll();
        return array.size();
    }

    /********************************************************************************************/

    public int insertOrUpdate(Preferences preferences)
    {
        int number = getCount();
        int result;

        if (number > 0)
        {
            result = update(preferences);
        }
        else
        {
            result = insert(preferences);
        }

        return result;
    }

    public Preferences getPreferences()
    {
        Preferences preferences = null;

        DatabaseHelper db = new DatabaseHelper(context);
        Cursor result = db.query(TABLE_NAME, COLUMNS, null, null, null);

        if (result.getCount() > 0)
        {
            result.moveToNext();
            preferences = new Preferences(result);
        }

        db.close();
        return preferences;
    }

    public void insertNewPreference()
    {
        String defaultProvinceCode = context.getResources().getString(R.string.default_province_code);
        String defaultCurrencyCode = context.getResources().getString(R.string.default_currency_code);

        Preferences preferences = new Preferences();
        preferences.setAutoUpdate(false);
        preferences.setProvinceCode(defaultProvinceCode);
        preferences.setCurrencyCode(defaultCurrencyCode);
        preferences.setProvinceUpdateDate(null);
        preferences.setUpdateDate(new Date());

        insert(preferences);
    }

    public ArrayList<Preferences> selectAll()
    {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null);

        ArrayList<Preferences> array = new ArrayList<Preferences>();

        while(cursor.moveToNext())
        {
            Preferences preferences = new Preferences(cursor);
            array.add(preferences);
        }

        cursor.close();
        db.close();

        return array;
    }
}
