package com.calixinteractive.taxcalculator.schema;

/**
 * Created by mayca on 12/07/2017.
 */

public interface ExchangeSchema
{
    String TABLE_NAME = "exchange";

    String COLUMN_ID            = "_id";
    String COLUMN_ID_WISH_LIST  = "id_wish_list";
    String COLUMN_INIT_PRICE    = "init_price";
    String COLUMN_CURRENCY      = "currency_rate";
    String COLUMN_TOTAL_TAXES   = "total_taxes";
    String COLUMN_DATE          = "update_date";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_ID_WISH_LIST
            + " INTEGER, "
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

    String  UPDATE_WISH_LIST = "UPDATE " + TABLE_NAME + " SET " + COLUMN_ID_WISH_LIST + " = 0 WHERE " + COLUMN_ID_WISH_LIST + " = ? ;";

    String SELECTION_BY_ID = COLUMN_ID + " = ?";

    String SELECTION_BY_WISHLIST_ID = COLUMN_ID_WISH_LIST + " = ?";

    String[] COLUMNS = new String[] { COLUMN_ID, COLUMN_ID_WISH_LIST, COLUMN_INIT_PRICE, COLUMN_TOTAL_TAXES, COLUMN_CURRENCY, COLUMN_DATE};
}
