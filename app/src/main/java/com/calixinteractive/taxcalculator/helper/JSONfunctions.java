package com.calixinteractive.taxcalculator.helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by maylcf on 09/07/2017.
 */

public class JSONfunctions
{
    public JSONfunctions() {
    }

    public static JSONObject getJSONfromURL(String url)
    {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        HttpResponse line;
        try {
            DefaultHttpClient e = new DefaultHttpClient();
            HttpPost sb = new HttpPost(url);
            line = e.execute(sb);
            HttpEntity entity = line.getEntity();
            is = entity.getContent();
        } catch (Exception var9) {
            Log.e("log_tag", "Error in http connection " + var9.toString());
        }

        try {
            BufferedReader e1 = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb1 = new StringBuilder();
            line = null;

            String line1;
            while((line1 = e1.readLine()) != null) {
                sb1.append(line1 + "\n");
            }

            is.close();
            result = sb1.toString();
        } catch (Exception var10) {
            Log.e("log_tag", "Error converting result " + var10.toString());
        }

        try {
            jArray = new JSONObject(result);
        } catch (JSONException var8) {
            Log.e("log_tag", "Error parsing data " + var8.toString());
        }

        return jArray;
    }
}
