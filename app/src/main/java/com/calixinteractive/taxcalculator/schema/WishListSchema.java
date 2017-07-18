package com.calixinteractive.taxcalculator.schema;

/**
 * Created by mayca on 14/07/2017.
 */

public interface WishListSchema
{
    String TABLE_NAME = "wish_list";

    String COLUMN_ID            = "_id";
    String COLUMN_NAME          = "name";
    String COLUMN_STORE         = "store";
    String COLUMN_INIT_PRICE    = "init_price";
    String COLUMN_TOTAL_TAXES   = "total_taxes";
    String COLUMN_CURRENCY      = "currency_rate";
    String COLUMN_DATE          = "update_date";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME
            + " TEXT NOT NULL,"
            + COLUMN_STORE
            + " TEXT NOT NULL,"
            + COLUMN_INIT_PRICE
            + " REAL,"
            + COLUMN_TOTAL_TAXES
            + " REAL,"
            + COLUMN_CURRENCY
            + " REAL,"
            + COLUMN_DATE
            + " TEXT "
            + ");";

    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    String SELECTION_BY_ID = COLUMN_ID + " = ?";

    String[] COLUMNS = new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_STORE, COLUMN_INIT_PRICE, COLUMN_TOTAL_TAXES, COLUMN_CURRENCY, COLUMN_DATE};
}
