package com.calixinteractive.taxcalculator.schema;

/**
 * Created by mayca on 12/07/2017.
 */

public interface ProvinceSchema
{
    String TABLE_NAME = "provinces";

    String COLUMN_ID     = "_id";
    String COLUMN_CODE   = "code";
    String COLUMN_NAME   = "name";
    String COLUMN_GST    = "gst";
    String COLUMN_PST    = "pst";
    String COLUMN_DATE   = "date";
    String COLUMN_UPDATE = "update_date";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_CODE
            + " TEXT NOT NULL,"
            + COLUMN_NAME
            + " TEXT NOT NULL,"
            + COLUMN_GST
            + " REAL,"
            + COLUMN_PST
            + " REAL,"
            + COLUMN_DATE
            + " TEXT,"
            + COLUMN_UPDATE
            + " TEXT "
            + ");";

    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    String SELECTION_BY_ID   = COLUMN_ID + " = ?";
    String SELECTION_BY_CODE = COLUMN_CODE + " = ?";

    String[] COLUMNS = new String[] { COLUMN_ID, COLUMN_CODE, COLUMN_NAME, COLUMN_GST, COLUMN_PST, COLUMN_DATE, COLUMN_UPDATE};
}
