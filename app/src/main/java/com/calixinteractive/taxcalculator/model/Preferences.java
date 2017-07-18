package com.calixinteractive.taxcalculator.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.calixinteractive.taxcalculator.helper.GeneralFunctions;
import com.calixinteractive.taxcalculator.schema.PreferencesSchema;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maylcf on 13/07/2017.
 */

public class Preferences implements Serializable
{
    private int     id;
    private Boolean autoUpdate;
    private String  provinceCode;
    private String  currencyCode;
    private Date    provinceUpdateDate;
    private Date    updateDate;

    /***************************************************************/

    public Preferences()
    {

    }

    public Preferences(Cursor c)
    {
        this.id                 = c.getInt(c.getColumnIndex(PreferencesSchema.COLUMN_ID));
        this.autoUpdate         = (c.getInt(c.getColumnIndex(PreferencesSchema.COLUMN_AUTO_UPDATE)) == 1);
        this.provinceCode       = c.getString(c.getColumnIndex(PreferencesSchema.COLUMN_PROV_CODE));
        this.currencyCode       = c.getString(c.getColumnIndex(PreferencesSchema.COLUMN_CURR_CODE));

        // Province Update Date
        String provDate         = c.getString(c.getColumnIndex(PreferencesSchema.COLUMN_PROV_UPDATE));
        this.provinceUpdateDate = GeneralFunctions.convertStringToDateTime(provDate);

        // Last Update Date
        String date      = c.getString(c.getColumnIndex(PreferencesSchema.COLUMN_DATE));
        this.updateDate  = GeneralFunctions.convertStringToDateTime(date);
    }

    /***************************************************************/

    public Boolean getAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getProvinceUpdateDate() {
        return provinceUpdateDate;
    }

    public void setProvinceUpdateDate(Date provinceUpdateDate) {
        this.provinceUpdateDate = provinceUpdateDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isTimeToUpdateProvinces()
    {
        boolean result = false;

        Date currentDate = new Date();

        if (this.provinceUpdateDate == null)
        {
            result = true;
        }
        else
        {
            int updateYear  = GeneralFunctions.getYear(this.provinceUpdateDate);
            int currentYear = GeneralFunctions.getYear(currentDate);

            // Return: the value 0 if x == y; a value less than 0 if x < y; and a value greater than 0 if x > y
            int comparison = Integer.compare(currentYear, updateYear);

            if(comparison > 0)
            {
                result = true;
            }
        }

        return result;
    }

    /***************************************************************/

    public ContentValues getContentValues()
    {
        ContentValues dados = new ContentValues();

        dados.put(PreferencesSchema.COLUMN_AUTO_UPDATE , this.autoUpdate);
        dados.put(PreferencesSchema.COLUMN_PROV_CODE   , this.provinceCode);
        dados.put(PreferencesSchema.COLUMN_CURR_CODE   , this.currencyCode);
        dados.put(PreferencesSchema.COLUMN_PROV_UPDATE , GeneralFunctions.convertDateTimeToString(this.provinceUpdateDate));
        dados.put(PreferencesSchema.COLUMN_DATE        , GeneralFunctions.convertDateTimeToString(this.updateDate));

        return dados;
    }
}
