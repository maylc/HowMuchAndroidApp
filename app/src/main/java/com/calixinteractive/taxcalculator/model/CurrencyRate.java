package com.calixinteractive.taxcalculator.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.GeneralFunctions;
import com.calixinteractive.taxcalculator.schema.CurrencyRateSchema;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maylcf on 12/07/2017.
 */

public class CurrencyRate implements Serializable
{
    private int     id;
    private String  time;
    private String  code;
    private String  date;
    private String  font;
    private Float   rate;
    private Boolean auto;
    private Date    updateDate;

    private final String AUTO_FONT = "Yahoo Finance";

    /***************************************************************/

    public CurrencyRate()
    {

    }

    public CurrencyRate(JSONObject json)
    {
        float exchangeRate = GeneralFunctions.formatFloat(json.optString("Ask"));

        code = json.optString("id");
        time = json.optString("Time");
        date = GeneralFunctions.convertUsStringToBrString(json.optString("Date"));
        font = AUTO_FONT;
        rate = exchangeRate;
        auto = true;
        updateDate = new Date();
    }

    public CurrencyRate(Cursor c)
    {
        auto = (c.getInt(c.getColumnIndex(CurrencyRateSchema.COLUMN_AUTO)) == 1);
        id   = c.getInt(c.getColumnIndex(CurrencyRateSchema.COLUMN_ID));
        code = c.getString(c.getColumnIndex(CurrencyRateSchema.COLUMN_CODE));
        time = c.getString(c.getColumnIndex(CurrencyRateSchema.COLUMN_TIME));
        date = c.getString(c.getColumnIndex(CurrencyRateSchema.COLUMN_DATE));
        font = c.getString(c.getColumnIndex(CurrencyRateSchema.COLUMN_FONT));
        rate = c.getFloat(c.getColumnIndex(CurrencyRateSchema.COLUMN_RATE));
        updateDate  = GeneralFunctions.convertStringToDateTime(c.getString(c.getColumnIndex(CurrencyRateSchema.COLUMN_UP_DATE)));
    }

    /***************************************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public void setAuto(String auto)
    {
        this.auto = Boolean.valueOf(auto);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /***************************************************************/

    public ContentValues getContentValues()
    {
        ContentValues dados = new ContentValues();

        dados.put(CurrencyRateSchema.COLUMN_CODE    , this.code);
        dados.put(CurrencyRateSchema.COLUMN_TIME    , this.time);
        dados.put(CurrencyRateSchema.COLUMN_DATE    , this.date);
        dados.put(CurrencyRateSchema.COLUMN_FONT    , this.font);
        dados.put(CurrencyRateSchema.COLUMN_RATE    , this.rate);
        dados.put(CurrencyRateSchema.COLUMN_AUTO    , this.auto);
        dados.put(CurrencyRateSchema.COLUMN_UP_DATE , GeneralFunctions.convertDateTimeToString(this.updateDate));

        return dados;
    }

    /*********************************************************************************************/
    // Format Data to Screen
    /*********************************************************************************************/

    public String getRateValueToScreen()
    {
        if (this.rate != null)
            return "$" + GeneralFunctions.getFloatFormat(this.rate);
        else
            return "";
    }

    public String getDateToScreen()
    {
        String result;
        if (this.date != null)
            result = this.date;
        else
            result = "";

        return result;
    }

}
