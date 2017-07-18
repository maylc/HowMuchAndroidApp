package com.calixinteractive.taxcalculator.schema;

/**
 * Created by mayca on 12/07/2017.
 */

public interface CurrencyRateSchema
{
    String TABLE_NAME = "currency_rate";

    String COLUMN_ID   = "_id";
    String COLUMN_CODE  = "code";
    String COLUMN_TIME = "time";
    String COLUMN_DATE = "date";
    String COLUMN_RATE = "rate";
    String COLUMN_FONT = "font";
    String COLUMN_AUTO = "auto";
    String COLUMN_UP_DATE = "update_date";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_CODE
            + " TEXT,"
            + COLUMN_FONT
            + " TEXT,"
            + COLUMN_TIME
            + " TEXT,"
            + COLUMN_DATE
            + " TEXT,"
            + COLUMN_AUTO
            + " TEXT,"
            + COLUMN_RATE
            + " REAL,"
            + COLUMN_UP_DATE
            + " TEXT "
            + ");";

    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    String SELECTION_BY_ID = COLUMN_ID + " = ?";

    String[] COLUMNS = new String[] { COLUMN_ID
            , COLUMN_CODE
            , COLUMN_TIME
            , COLUMN_DATE
            , COLUMN_FONT
            , COLUMN_AUTO
            , COLUMN_RATE
            , COLUMN_UP_DATE};
}
