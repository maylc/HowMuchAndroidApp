package com.calixinteractive.taxcalculator.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.calixinteractive.taxcalculator.dao.ProvincesDao;
import com.calixinteractive.taxcalculator.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mayca on 12/07/2017.
 */

public class GetProviceTax extends AsyncTask<Void, Void, String>
{
    private Context context;
    private ProgressDialog dialog;
    Map<String, String> privinceNames;

    public GetProviceTax(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        dialog = ProgressDialog.show(context, "Please Wait", "Retrieving provinces information", true, true);
    }

    @Override
    protected String doInBackground(Void... params)
    {
        privinceNames = getProvinceNames();

        /**********************************************************************************/

        ProvincesDao dao = new ProvincesDao(context);

        /**********************************************************************************/

        try
        {
            URL url = new URL("http://api.salestaxapi.ca/v2/province/all");
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
                JSONObject obj = new JSONObject(result);
                Iterator x = obj.keys();

                while (x.hasNext())
                {
                    String key  = (String) x.next();
                    String name = privinceNames.get(key);
                    JSONObject json = (JSONObject) obj.get(key);
                    Province province = new Province(json, key, name);
                    dao.insertOrUpdate(province);
                }
            }

            /**********************************************************************************/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "success";

    }

    @Override
    protected void onPostExecute(String resposta)
    {
        dialog.dismiss();
        //Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }

    private Map<String, String> getProvinceNames()
    {
        Map<String, String> map = new HashMap<String,String>();

        map.put("ab", "Alberta");
        map.put("bc", "British Columbia");
        map.put("mb", "Manitoba");
        map.put("nb", "New Brunswick");
        map.put("nl", "Newfoundland and Labrador");
        map.put("nt", "Northwest Territories");
        map.put("ns", "Nova Scotia");
        map.put("nu", "Nunavut");
        map.put("on", "Ontario");
        map.put("pe", "Prince Edward Island");
        map.put("qc", "Quebec");
        map.put("sk", "Saskatchewan");
        map.put("yt", "Yukon");

        return map;
    }
}
