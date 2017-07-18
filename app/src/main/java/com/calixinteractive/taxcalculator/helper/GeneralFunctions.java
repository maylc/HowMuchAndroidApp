package com.calixinteractive.taxcalculator.helper;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maylcf on 09/07/2017.
 */

public class GeneralFunctions
{
    private Context context;
    public  static String YAHOO_FINANCE_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22CADBRL%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    public  static String PROVINCES_TAX_URL = "http://api.salestaxapi.ca/v2/province/all";

    public static SimpleDateFormat BRAZILIAN_DATE_FORMAT     = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat AMERICAN_DATE_FORMAT     = new SimpleDateFormat("MM/dd/yyyy");
    private static SimpleDateFormat SIMPLE_TIME_FORMAT       = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat FULL_DATE_TIME_FORMAT_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static SimpleDateFormat FULL_DATE_TIME_FORMAT_US = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    /*********************************************************************************************/
    // String to Date
    /*********************************************************************************************/

    public static Date convertUsStringToDate(String date)
    {
        Date result;

        try
        {
            Date convertedDate = AMERICAN_DATE_FORMAT.parse(date);
            String currentDateandTime = BRAZILIAN_DATE_FORMAT.format(convertedDate);
            result = BRAZILIAN_DATE_FORMAT.parse(currentDateandTime);
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public static String convertUsStringToBrString(String date)
    {
        String result;

        try
        {
            Date convertedDate = AMERICAN_DATE_FORMAT.parse(date);
            result = BRAZILIAN_DATE_FORMAT.format(convertedDate);
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public static Date convertBrStringToDate(String date)
    {
        Date result;

        try
        {
            result = BRAZILIAN_DATE_FORMAT.parse(date);
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public static Date convertStringToDateTime(String date)
    {
        Date result;

        try
        {
            result = FULL_DATE_TIME_FORMAT_BR.parse(date);
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    /*********************************************************************************************/
    // Date to String
    /*********************************************************************************************/

    public static String convertDateTimeToString(Date date)
    {
        String result;

        try
        {
            result = FULL_DATE_TIME_FORMAT_BR.format(date);
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public static String convertDateToString(Date date)
    {
        String result;

        try
        {
            result = BRAZILIAN_DATE_FORMAT.format(date);
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    public static String getCurrentDate()
    {
        String currentDateandTime = BRAZILIAN_DATE_FORMAT.format(new Date());
        return currentDateandTime;
    }

    public static String getCurrentTime()
    {
        String currentDateandTime = SIMPLE_TIME_FORMAT.format(new Date());
        return currentDateandTime;
    }

    public static int getYear(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String yearStr = dateFormat.format(date);
        return Integer.valueOf(yearStr);
    }

    /*********************************************************************************************/
    // Float Format
    /*********************************************************************************************/

    public static String getFloatFormat(String value)
    {
        String newValue;
        if (!(value == null))
        {
            if (!value.isEmpty())
            {
                float exchangeRateFloat = Float.valueOf(value);

                newValue = String.format("%.2f", exchangeRateFloat);
                newValue = newValue.replace(',','.');

                return newValue;
            }
        }

        return "";
    }

    public static Float formatFloat(String value)
    {
        String newValue;

        if (!(value == null))
        {
            if (!value.isEmpty())
            {
                float initFloat = Float.valueOf(value);

                newValue = String.format("%.2f", initFloat);
                newValue = newValue.replace(',','.');

                float newFloat = Float.valueOf(newValue).floatValue();


                return newFloat;
            }
        }

        return 0.0f;
    }

    public static String getFloatFormat(Float value)
    {
        String newValue;

        if (!(value == null))
        {
            newValue = String.format("%.2f", value);
            newValue = newValue.replace(',','.');
            return newValue;
        }
        else
        {
            return "";
        }
    }
}
