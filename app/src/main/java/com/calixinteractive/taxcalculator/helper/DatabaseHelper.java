package com.calixinteractive.taxcalculator.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.calixinteractive.taxcalculator.schema.CurrencyRateSchema;
import com.calixinteractive.taxcalculator.schema.ExchangeSchema;
import com.calixinteractive.taxcalculator.schema.PreferencesSchema;
import com.calixinteractive.taxcalculator.schema.ProvinceSchema;
import com.calixinteractive.taxcalculator.schema.WishListSchema;

/**
 * Created by maylcf on 12/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(ProvinceSchema.CREATE_TABLE);
        db.execSQL(ExchangeSchema.CREATE_TABLE);
        db.execSQL(CurrencyRateSchema.CREATE_TABLE);
        db.execSQL(PreferencesSchema.CREATE_TABLE);
        db.execSQL(WishListSchema.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(ProvinceSchema.DROP_TABLE);
        db.execSQL(ExchangeSchema.DROP_TABLE);
        db.execSQL(CurrencyRateSchema.DROP_TABLE);
        db.execSQL(PreferencesSchema.DROP_TABLE);
        db.execSQL(WishListSchema.DROP_TABLE);

        onCreate(db);
    }

    /********************************************************************************************/

    public int insert(String tableName, ContentValues contentValues)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(tableName, null, contentValues);
        db.close();

        return (int) result;
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = db.update(tableName, values, selection, selectionArgs);
        db.close();

        return (int) result;
    }

    public int delete(String tableName, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(tableName, selection, selectionArgs);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder, String limit)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, sortOrder, limit);
        return cursor;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        return cursor;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.rawQuery(sql, selectionArgs);
        db.close();

        return result;
    }

    public int count(String sql)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int cnt = cursor.getCount();
        return cnt;
    }

}
