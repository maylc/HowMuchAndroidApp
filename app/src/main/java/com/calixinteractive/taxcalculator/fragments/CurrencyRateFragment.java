package com.calixinteractive.taxcalculator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.calixinteractive.taxcalculator.MainActivity;
import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.dao.CurrencyRateDao;
import com.calixinteractive.taxcalculator.helper.GeneralFunctions;
import com.calixinteractive.taxcalculator.helper.MessageHelper;
import com.calixinteractive.taxcalculator.model.CurrencyRate;
import com.calixinteractive.taxcalculator.task.GetExchangeRate;

/**
 * Created by maylcf on 12/07/2017.
 */

public class CurrencyRateFragment extends Fragment
{
    private View view;
    private MainActivity mainActivity;

    private Switch      autoFillForm;
    private Button      btnSave;
    private EditText    source;
    private EditText    date;
    private EditText    valRate;

    private CurrencyRateDao currencyRateDao;
    private CurrencyRate currencyRate;
    private MessageHelper messageHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_currency_rate, container, false);

        /****************************************************************************************/
        // Get View Components

        autoFillForm = (Switch)      view.findViewById(R.id.frag_currency_switch);
        btnSave      = (Button)      view.findViewById(R.id.frag_currency_btn_save);
        source       = (EditText)    view.findViewById(R.id.frag_currency_font);
        date         = (EditText)    view.findViewById(R.id.frag_currency_date);
        valRate      = (EditText)    view.findViewById(R.id.frag_currency_value);

        /****************************************************************************************/
        // Get Other Components

        mainActivity    = (MainActivity) getActivity();
        currencyRateDao = new CurrencyRateDao(mainActivity);
        messageHelper   = new MessageHelper(mainActivity);
        currencyRate    = currencyRateDao.getCurrencyRate();

        /****************************************************************************************/
        // Set Title

        mainActivity.setTitle(messageHelper.getCurrencyRateFragmentTitle());

        /****************************************************************************************/
        // Load Screen

        loadScreen();

        /****************************************************************************************/
        // Auto update Switch -

        autoFillForm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                changeCurrencyMode(isChecked);
            }

        });

        /****************************************************************************************/
        // Save Button - Manual Input Only

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveManualInput();
            }
        });

        /****************************************************************************************/
        // Return View

        return view;
    }

    private void loadScreen()
    {
        autoFillForm.setChecked(currencyRate.getAuto());
        source.setText(currencyRate.getFont());
        date.setText(currencyRate.getDateToScreen());
        valRate.setText(currencyRate.getRateValueToScreen());
        enableComponents(currencyRate.getAuto());
    }

    private void changeCurrencyMode(Boolean isChecked)
    {
        String requestResult;

        if (isChecked)
        {
            requestResult = requestAutoCurrencyRate();
            currencyRate  = currencyRateDao.getCurrencyRate();
        }
        else
        {
            currencyRate.setAuto(isChecked);
            currencyRate.setRate(null);
            currencyRate.setFont(null);
            currencyRate.setDate(null);
            currencyRate.setTime(null);
            currencyRate.setCode(null);
        }

        loadScreen();
    }

    private String requestAutoCurrencyRate()
    {
        String result = null;

        try
        {
            result = new GetExchangeRate(mainActivity).execute().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private void saveManualInput()
    {

        /****************************************************************************************/
        // Get User Input

        if ( valRate.getText().toString().isEmpty())
        {
            Toast.makeText(mainActivity, "Informe um valor válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Float input = GeneralFunctions.formatFloat(valRate.getText().toString());

        /****************************************************************************************/
        // Populate Object

        currencyRate.setAuto(false);
        currencyRate.setRate(input);
        currencyRate.setFont(messageHelper.getManualInputFont());
        currencyRate.setDate(GeneralFunctions.getCurrentDate());
        currencyRate.setTime(GeneralFunctions.getCurrentTime());
        currencyRate.setCode(messageHelper.getManualInputCode());

        /****************************************************************************************/
        // Update Table

        currencyRateDao.insertOrUpdate(currencyRate);

        /****************************************************************************************/
        // Load Screen

        loadScreen();
    }

    private void enableComponents(Boolean isAuto)
    {
        if (isAuto)
        {
            btnSave.setVisibility(View.INVISIBLE);
            valRate.setEnabled(false);
        }
        else
        {
            btnSave.setVisibility(View.VISIBLE);
            valRate.setEnabled(true);
        }
    }
}
