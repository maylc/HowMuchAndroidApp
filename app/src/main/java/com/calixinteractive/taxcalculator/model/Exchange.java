package com.calixinteractive.taxcalculator.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.calixinteractive.taxcalculator.helper.GeneralFunctions;
import com.calixinteractive.taxcalculator.schema.ExchangeSchema;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maylcf on 12/07/2017.
 */

public class Exchange implements Serializable
{
    private int    id;
    private int    idWishList;

    private Float  initPrice;
    private Float  currencyRate;
    private Float  totalTaxes;
    private Date   updateDate;

    /***************************************************************/

    public Exchange ()
    {

    }

    public Exchange(Cursor c)
    {
        this.id            = c.getInt(c.getColumnIndex(ExchangeSchema.COLUMN_ID));
        this.idWishList    = c.getInt(c.getColumnIndex(ExchangeSchema.COLUMN_ID_WISH_LIST));
        this.initPrice     = c.getFloat(c.getColumnIndex(ExchangeSchema.COLUMN_INIT_PRICE));
        this.currencyRate  = c.getFloat(c.getColumnIndex(ExchangeSchema.COLUMN_CURRENCY));
        this.totalTaxes    = c.getFloat(c.getColumnIndex(ExchangeSchema.COLUMN_TOTAL_TAXES));
        this.updateDate    = GeneralFunctions.convertStringToDateTime(c.getString(c.getColumnIndex(ExchangeSchema.COLUMN_DATE)));
    }

    /***************************************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Float initPrice) {
        this.initPrice = initPrice;
    }

    public Float getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(Float totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Float currencyRate) {
        this.currencyRate = currencyRate;
    }

    public int getIdWishList() {
        return idWishList;
    }

    public void setIdWishList(int idWishList) {
        this.idWishList = idWishList;
    }

    /***************************************************************/

    public ContentValues getContentValues()
    {
        ContentValues dados = new ContentValues();

        dados.put(ExchangeSchema.COLUMN_ID_WISH_LIST, this.idWishList);
        dados.put(ExchangeSchema.COLUMN_INIT_PRICE, this.initPrice);
        dados.put(ExchangeSchema.COLUMN_CURRENCY, this.currencyRate);
        dados.put(ExchangeSchema.COLUMN_TOTAL_TAXES, this.totalTaxes);
        dados.put(ExchangeSchema.COLUMN_DATE, GeneralFunctions.convertDateTimeToString(this.updateDate));

        return dados;
    }
    /*********************************************************************************************/
    // Calculations
    /*********************************************************************************************/

    public Float getPercentageOfTaxes()
    {
        Float result;

        try
        {
            result = (totalTaxes * 100)/initPrice;
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public Float getPriceWithTaxes()
    {
        Float result;

        try
        {
            result = totalTaxes + initPrice;
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public Float getConvertedPrice()
    {
        Float convertedPrice;

        try
        {
            Float priceWithTaxes = getPriceWithTaxes();
            convertedPrice = priceWithTaxes * this.currencyRate;
        }
        catch (Exception e)
        {
            convertedPrice = null;
        }

        return convertedPrice;
    }

    public void calcTotalTaxes(float gst, float pst)
    {
        Float calcTotalTaxes;

        try
        {
            Float calcItemGstTax = initPrice * gst;
            Float calcItemPstTax = initPrice * pst;
            calcTotalTaxes = calcItemGstTax + calcItemPstTax;
        }
        catch (Exception e)
        {
            calcTotalTaxes = null;
        }

        this.totalTaxes = calcTotalTaxes;
    }

    /*********************************************************************************************/
    // Format Data to Screen
    /*********************************************************************************************/

    public String getInitPriceToScreen()
    {
        if (this.initPrice != null)
            return "$" + GeneralFunctions.getFloatFormat(this.initPrice);
        else
            return "";
    }

    public String getInitPriceInputToScreen()
    {
        if (this.initPrice != null)
            return GeneralFunctions.getFloatFormat(this.initPrice);
        else
            return "";
    }


    public String getFinalPriceToScreen()
    {
        if (getPriceWithTaxes() != null)
            return "$" + GeneralFunctions.getFloatFormat(getPriceWithTaxes());
        else
            return "";
    }

    public String getTotalTaxesWithPercToScreen()
    {
        String result = "";
        String percTaxes = "";

        if (getTotalTaxes() != null)
        {
            percTaxes = "(" + GeneralFunctions.getFloatFormat(getPercentageOfTaxes()) + "%)";
            result = "$" + GeneralFunctions.getFloatFormat(getTotalTaxes()) + " " + percTaxes;
        }

        return result;
    }

    public String getCurrencyRateToScreen()
    {
        if (this.currencyRate != null)
            return GeneralFunctions.getFloatFormat(getCurrencyRate());
        else
            return "";
    }

    public String getConvertedPriceToScreen()
    {
        if (this.getConvertedPrice() != null)
            return "R$"+ GeneralFunctions.getFloatFormat(getConvertedPrice());
        else
            return "";
    }
}
