package com.calixinteractive.taxcalculator.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.calixinteractive.taxcalculator.dao.CurrencyRateDao;
import com.calixinteractive.taxcalculator.model.CurrencyRate;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mayca on 12/07/2017.
 */

public class GetExchangeRate extends AsyncTask<Void, Void, String>
{
    private Context context;
    private ProgressDialog dialog;
    String yahooUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22CADBRL%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

    public GetExchangeRate(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        dialog = ProgressDialog.show(context, "Please Wait", "Retrieving exchange rate information", true, false);
    }

    @Override
    protected String doInBackground(Void... params)
    {

        CurrencyRateDao dao = new CurrencyRateDao(context);

        /**********************************************************************************/

        try
        {
            URL url = new URL(yahooUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String result = rd.readLine();

            if (result != null)
            {
                JSONObject request      = new JSONObject(result);
                JSONObject json_query   = request.getJSONObject("query");
                JSONObject json_results = json_query.getJSONObject("results");
                JSONObject json_rate    = json_results.getJSONObject("rate");

                CurrencyRate currency = new CurrencyRate(json_rate);

                dao.insertOrUpdate(currency);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";

    }

    @Override
    protected void onPostExecute(String resposta)
    {
        dialog.dismiss();
    }
}
