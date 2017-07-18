package com.calixinteractive.taxcalculator.schema;

/**
 * Created by mayca on 13/07/2017.
 */

public interface PreferencesSchema
{
    String TABLE_NAME = "settings";

    String COLUMN_ID          = "_id";
    String COLUMN_AUTO_UPDATE = "auto_update";
    String COLUMN_PROV_CODE   = "province_code";
    String COLUMN_PROV_UPDATE = "province_update_date";
    String COLUMN_CURR_CODE   = "currency_code";
    String COLUMN_DATE        = "update_date";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_AUTO_UPDATE
            + " TEXT NOT NULL,"
            + COLUMN_PROV_CODE
            + " TEXT NOT NULL,"
            + COLUMN_CURR_CODE
            + " TEXT NOT NULL,"
            + COLUMN_PROV_UPDATE
            + " TEXT,"
            + COLUMN_DATE
            + " TEXT "
            + ");";

    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    String SELECTION_BY_ID = COLUMN_ID + " = ?";

    String[] COLUMNS = new String[] { COLUMN_ID, COLUMN_AUTO_UPDATE, COLUMN_PROV_CODE, COLUMN_CURR_CODE, COLUMN_PROV_UPDATE, COLUMN_DATE};
}
