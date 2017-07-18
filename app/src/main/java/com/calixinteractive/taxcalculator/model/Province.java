package com.calixinteractive.taxcalculator.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.GeneralFunctions;
import com.calixinteractive.taxcalculator.schema.ProvinceSchema;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maylcf on 12/07/2017.
 */

public class Province implements Serializable
{
    private int    id;
    private String name;
    private String code;
    private Float  gst;
    private Float  pst;
    private String date;
    private Date   updateDate;

    /***************************************************************/

    public Province()
    {

    }

    public Province(JSONObject json, String key, String name)
    {
        this.code = key;
        this.name = name;
        this.gst  = Float.valueOf(json.optString("gst"));
        this.pst  = Float.valueOf(json.optString("pst"));
        this.date = json.optString("updated_at");
    }

    public Province(Cursor c)
    {
        this.id         = c.getInt(c.getColumnIndex(ProvinceSchema.COLUMN_ID));
        this.code       = c.getString(c.getColumnIndex(ProvinceSchema.COLUMN_CODE));
        this.name       = c.getString(c.getColumnIndex(ProvinceSchema.COLUMN_NAME));
        this.gst        = c.getFloat(c.getColumnIndex(ProvinceSchema.COLUMN_GST));
        this.pst        = c.getFloat(c.getColumnIndex(ProvinceSchema.COLUMN_PST));
        this.date       = c.getString(c.getColumnIndex(ProvinceSchema.COLUMN_DATE));
        this.updateDate = GeneralFunctions.convertStringToDateTime(c.getString(c.getColumnIndex(ProvinceSchema.COLUMN_UPDATE)));
    }

    /***************************************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getGst() {
        return gst;
    }

    public void setGst(Float gst) {
        this.gst = gst;
    }

    public Float getPst() {
        return pst;
    }

    public void setPst(Float pst) {
        this.pst = pst;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /***************************************************************/

    public ContentValues getContentValues()
    {
        ContentValues dados = new ContentValues();

        dados.put(ProvinceSchema.COLUMN_NAME   , this.name);
        dados.put(ProvinceSchema.COLUMN_CODE   , this.code);
        dados.put(ProvinceSchema.COLUMN_GST    , this.gst);
        dados.put(ProvinceSchema.COLUMN_PST    , this.pst);
        dados.put(ProvinceSchema.COLUMN_DATE   , this.date);
        dados.put(ProvinceSchema.COLUMN_UPDATE , GeneralFunctions.convertDateTimeToString(this.updateDate));

        return dados;
    }

    @Override
    public String toString()
    {
        return this.code.toUpperCase() + " - " + this.name;
    }
}
