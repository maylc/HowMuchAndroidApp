package com.calixinteractive.taxcalculator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.calixinteractive.taxcalculator.dao.CurrencyRateDao;
import com.calixinteractive.taxcalculator.dao.PreferencesDao;
import com.calixinteractive.taxcalculator.dao.ProvincesDao;
import com.calixinteractive.taxcalculator.fragments.CurrencyRateFragment;
import com.calixinteractive.taxcalculator.fragments.ExchangeFragment;
import com.calixinteractive.taxcalculator.fragments.PreferenceFragment;
import com.calixinteractive.taxcalculator.fragments.WishListFragment;
import com.calixinteractive.taxcalculator.model.CurrencyRate;
import com.calixinteractive.taxcalculator.model.Preferences;
import com.calixinteractive.taxcalculator.task.GetExchangeRate;
import com.calixinteractive.taxcalculator.task.GetProviceTax;

import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            FragmentManager fragmentManager = getFragmentManager();

            switch (item.getItemId())
            {
                case R.id.navigation_exchange:
                    fragmentManager.beginTransaction().replace(R.id.main_content, new ExchangeFragment()).commit();
                    return true;
                case R.id.navigation_wish_list:
                    fragmentManager.beginTransaction().replace(R.id.main_content, new WishListFragment()).commit();
                    return true;
                case R.id.navigation_currency_rate:
                    fragmentManager.beginTransaction().replace(R.id.main_content, new CurrencyRateFragment()).commit();
                    return true;
                case R.id.navigation_preferences:
                    fragmentManager.beginTransaction().replace(R.id.main_content, new PreferenceFragment()).commit();
                    return true;
            }

            return false;
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /**********************************************************************************/
        // Initializes Daos

        PreferencesDao preferencesDao = new PreferencesDao(this);
        ProvincesDao provincesDao = new ProvincesDao(this);
        CurrencyRateDao currencyRateDao = new CurrencyRateDao(this);

        /**********************************************************************************/
        // Generates Base tables - If necessary

        if (preferencesDao.getCount() == 0)
        {
            preferencesDao.insertNewPreference();
        }

        if (currencyRateDao.getCount() == 0)
        {
            currencyRateDao.insertFirstCurrencyRate();
        }

        /**********************************************************************************/
        // Initializes Objects

        Preferences preferences   = preferencesDao.getPreferences();
        CurrencyRate currencyRate = currencyRateDao.getCurrencyRate();

        /**********************************************************************************/
        // Update Currency Rate - If necessary

        if (currencyRate != null)
        {
            if (currencyRate.getAuto())
            {
                Float zero = 0.0f;
                if ( preferences.getAutoUpdate() || currencyRate.getRate().equals(zero) )
                {
                    try
                    {
                        String str_result = new GetExchangeRate(this).execute().get();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**********************************************************************************/
        // Populate Provinces - If necessary

        if (provincesDao.getCount() == 0 || preferences.isTimeToUpdateProvinces() )
        {
            try
            {
                String str_result = String.valueOf(new GetProviceTax(this).execute(null, null, null).get());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            preferences.setProvinceUpdateDate(new Date());
            preferencesDao.insertOrUpdate(preferences);
        }

        /**********************************************************************************/
        // Go to first tab

        if (savedInstanceState == null)
        {
            navigation.getMenu().performIdentifierAction(R.id.navigation_exchange, 0);
        }
    }
}
