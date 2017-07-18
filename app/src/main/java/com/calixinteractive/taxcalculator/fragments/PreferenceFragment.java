package com.calixinteractive.taxcalculator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.calixinteractive.taxcalculator.MainActivity;
import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.dao.PreferencesDao;
import com.calixinteractive.taxcalculator.dao.ProvincesDao;
import com.calixinteractive.taxcalculator.helper.MessageHelper;
import com.calixinteractive.taxcalculator.helper.OnSpinnerItemClick;
import com.calixinteractive.taxcalculator.helper.SimpleSpinnerDialog;
import com.calixinteractive.taxcalculator.model.Preferences;
import com.calixinteractive.taxcalculator.model.Province;

import java.util.ArrayList;

/**
 * Created by maylcf on 12/07/2017.
 */

public class PreferenceFragment extends Fragment
{
    private View         view;
    private MainActivity mainActivity;
    private LinearLayout provinceGroup;
    private TextView     provinceName;
    private Switch       swAutoUpdate;

    private PreferencesDao preferencesDao;
    private ProvincesDao provincesDao;
    private Preferences    preferences;
    private MessageHelper  messageHelper;

    private ArrayList<Province> arrayOfProvinces;
    private String selectedProvinceCode;

    private SimpleSpinnerDialog spinnerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_preferences, container, false);

        /****************************************************************************************/
        // Get View Components

        swAutoUpdate  = (Switch)  view.findViewById(R.id.frag_preferences_update);
        provinceGroup = (LinearLayout) view.findViewById(R.id.frag_preferences_province_group);
        provinceName  = (TextView) view.findViewById(R.id.frag_preferences_province_name);

        /****************************************************************************************/
        // Get Other Components

        mainActivity     = (MainActivity) getActivity();
        preferencesDao   = new PreferencesDao(mainActivity);
        provincesDao     = new ProvincesDao(mainActivity);
        arrayOfProvinces = provincesDao.selectAll();
        preferences      = preferencesDao.getPreferences();

        messageHelper = new MessageHelper(mainActivity);

        /****************************************************************************************/
        // Set Title

        mainActivity.setTitle(messageHelper.getPreferencesFragmentTitle());

        /****************************************************************************************/
        // Load Screen

        loadScreen();

        /****************************************************************************************/
        // Build Provinces Spinner Dialog

        spinnerDialog = new SimpleSpinnerDialog(mainActivity, arrayOfProvinces, messageHelper.getProvinceDialogTitle());

        spinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick()
        {
            @Override
            public void onClick(Object item, int position)
            {
                Province selected    = (Province) item;
                selectedProvinceCode = selected.getCode();
                updateValues();
            }
        });

        /****************************************************************************************/
        // Province Selection Dialog - OnClickListener

        provinceGroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                spinnerDialog.show();
            }
        });

        /****************************************************************************************/
        // Switch Auto Update

        swAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                updateValues();
            }

        });

        /****************************************************************************************/
        // Return View

        return view;
    }

    private void loadScreen()
    {
        String name = "";

        if (preferences.getProvinceCode() != null)
        {
            Province p  = provincesDao.selectByCode(preferences.getProvinceCode());
            name = p.toString();
        }

        swAutoUpdate.setChecked(preferences.getAutoUpdate());
        provinceName.setText(name);
    }

    private void updateValues()
    {
        //Province Code
        if (selectedProvinceCode != null) preferences.setProvinceCode(selectedProvinceCode);

        // Auto Update
        preferences.setAutoUpdate(swAutoUpdate.isChecked());

        // Update Table
        preferencesDao.insertOrUpdate(preferences);

        // Msg to User
        Toast.makeText(mainActivity, messageHelper.getSuccessUpdateMsg(), Toast.LENGTH_SHORT).show();

        //Update Screen
        loadScreen();
    }

}
