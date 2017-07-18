package com.calixinteractive.taxcalculator.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.calixinteractive.taxcalculator.MainActivity;
import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.dao.CurrencyRateDao;
import com.calixinteractive.taxcalculator.dao.ExchangeDao;
import com.calixinteractive.taxcalculator.dao.PreferencesDao;
import com.calixinteractive.taxcalculator.dao.ProvincesDao;
import com.calixinteractive.taxcalculator.dao.WishListDao;
import com.calixinteractive.taxcalculator.helper.MessageHelper;
import com.calixinteractive.taxcalculator.model.CurrencyRate;
import com.calixinteractive.taxcalculator.model.Exchange;
import com.calixinteractive.taxcalculator.model.Preferences;
import com.calixinteractive.taxcalculator.model.Province;
import com.calixinteractive.taxcalculator.model.WishList;

/**
 * Created by maylcf on 12/07/2017.
 */

public class ExchangeFragment extends Fragment
{
    private View view;

    private MainActivity mainActivity;
    private ExchangeDao exchangeDao;
    private Exchange      exchange;
    private MessageHelper messageHelper;

    private EditText    initPrice;
    private EditText    totalTaxes;
    private EditText    finalPrice;
    private EditText    currencyRate;
    private EditText    convertPrice;
    private EditText    dialogName;
    private EditText    dialogStore;

    private ImageButton btnUpdate;
    private ImageButton btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_exchange, container, false);
        /****************************************************************************************/
        // Get View Components

        initPrice      = (EditText)    view.findViewById(R.id.frag_exchange_price);
        totalTaxes     = (EditText)    view.findViewById(R.id.frag_exchange_total_taxes);
        finalPrice     = (EditText)    view.findViewById(R.id.frag_exchange_final_price);
        currencyRate   = (EditText)    view.findViewById(R.id.frag_exchange_currency_rate);
        convertPrice   = (EditText)    view.findViewById(R.id.frag_exchange_converted_price);
        btnUpdate      = (ImageButton) view.findViewById(R.id.frag_exchange_btn_update);
        btnSave        = (ImageButton) view.findViewById(R.id.frag_exchange_btn_save);

        /****************************************************************************************/
        // Get Other Components

        mainActivity   = (MainActivity) getActivity();
        exchangeDao    = new ExchangeDao(mainActivity);
        messageHelper  = new MessageHelper(mainActivity);

        /****************************************************************************************/
        // Set Title

        mainActivity.setTitle(messageHelper.getExchangeFragmentTitle());

        /****************************************************************************************/
        // Get Current Exchange - If Exists

        exchange  = exchangeDao.selectExchange();

        if ( exchange == null)
        {
            exchange = new Exchange();
            loadScreen();
        }
        else
        {
            loadScreen();
            calculate();
        }

        /****************************************************************************************/
        // Price Field Listener - Calculate

        initPrice.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if ( actionId == EditorInfo.IME_ACTION_DONE )
                {
                    if (!(String.valueOf(initPrice.getText()).isEmpty()))
                    {
                        calculate();
                    }
                    else
                    {
                        loadScreen();
                    }
                }

                return false;
            }
        });

        /****************************************************************************************/
        // Refresh Button

        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                calculate();
            }
        });

        /****************************************************************************************/
        // Save Button

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                calculate();
                showWishListDialog();
            }
        });

        /****************************************************************************************/
        // Return View

        return view;
    }

    private void loadScreen()
    {
        initPrice.setText(exchange.getInitPriceInputToScreen());
        totalTaxes.setText(exchange.getTotalTaxesWithPercToScreen());
        finalPrice.setText(exchange.getFinalPriceToScreen());
        currencyRate.setText(exchange.getCurrencyRateToScreen());
        convertPrice.setText(exchange.getConvertedPriceToScreen());

        if (exchange.getIdWishList() == 0)
        {
            btnSave.setImageResource(R.drawable.ic_heart);
        }
        else
        {
            btnSave.setImageResource(R.drawable.ic_heart_full);
        }

    }

    private void calculate()
    {
        /****************************************************************************************/
        // Get User Input

        Float price = validateUserInput();

        if (price == null) return;

        /****************************************************************************************/
        // Get Preferences

        Preferences preferences = validatePreference();

        if (preferences == null) return;

        /****************************************************************************************/
        //Get Province

        Province province = validateProvince(preferences);

        if (province == null) return;

        /****************************************************************************************/
        // Get Currency Rate

        CurrencyRate currencyRate = validateCurrencyRate();

        if (currencyRate == null) return;

        /****************************************************************************************/
        // Retrieve the necessary information

        Float provGST = province.getGst();
        Float provPST = province.getPst();
        Float curRate = currencyRate.getRate();

        /****************************************************************************************/
        // Calculate

        if (exchange.getInitPrice() != null )
        {
            if (!exchange.getInitPrice().equals(price))
            {
                exchange.setIdWishList(0);
            }
        }

        exchange.setInitPrice(price);
        exchange.setCurrencyRate(curRate);
        exchange.calcTotalTaxes(provGST, provPST);

        /****************************************************************************************/
        // Update Table

        exchangeDao.insertOrUpdate(exchange);

        /****************************************************************************************/
        // Update View

        loadScreen();
    }

    private void saveToWishList()
    {

        String inputName  = dialogName.getText().toString();
        String inputStore = dialogStore.getText().toString();
        String errorMsg   = getResources().getString(R.string.msg_dialog_wishlist_input_error);
        String successMsg = getResources().getString(R.string.msg_dialog_wishlist_success);
        String errorDbMsg = getResources().getString(R.string.msg_dialog_wishlist_db_error);

        /****************************************************************************************/
        // Validate Input

        if (inputName.replace(" ", "").isEmpty() || inputStore.replace(" ", "").isEmpty())
        {
            Toast.makeText(mainActivity, errorMsg, Toast.LENGTH_SHORT).show();
            return;
        }

        /****************************************************************************************/
        // Populate Object

        WishList wishList = new WishList();
        wishList.setName(inputName);
        wishList.setStore(inputStore);
        wishList.setInitPrice(exchange.getInitPrice());
        wishList.setCurrencyRate(exchange.getCurrencyRate());
        wishList.setTotalTaxes(exchange.getTotalTaxes());

        /****************************************************************************************/
        // Update Table

        WishListDao wishListDao = new WishListDao(mainActivity);
        long result = wishListDao.insert(wishList);

        if (result == -1)
        {
            Toast.makeText(mainActivity, errorDbMsg, Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            exchange.setIdWishList((int) result);
            exchangeDao.insertOrUpdate(exchange);
        }

        /****************************************************************************************/
        // Update View

        loadScreen();

        /****************************************************************************************/
        // Msg to User

        Toast.makeText(mainActivity, successMsg, Toast.LENGTH_SHORT).show();

    }

    /****************************************************************************************/
    // Validations
    /****************************************************************************************/

    private Float validateUserInput()
    {
        Float price = null;
        String userInput = initPrice.getText().toString();
        String msg = getResources().getString(R.string.exchange_form_invalid_value);

        if (userInput.isEmpty())
        {
            Toast.makeText(mainActivity.getApplication(), msg, Toast.LENGTH_SHORT).show();
        }

        try
        {
            price = Float.valueOf(userInput);
        }
        catch (Exception e)
        {
            Toast.makeText(mainActivity.getApplication(), msg, Toast.LENGTH_SHORT).show();
        }

        return price;
    }

    private CurrencyRate validateCurrencyRate()
    {
        CurrencyRateDao currencyRateDao = new CurrencyRateDao(mainActivity);
        CurrencyRate currencyRate = currencyRateDao.getCurrencyRate();

        if (currencyRate == null)
        {
            showCurrencyRateNotFoundDialog();
            return null;
        }
        else if (currencyRate.getRate() == null)
        {
            showCurrencyRateNotFoundDialog();
            return null;
        }

        return currencyRate;
    }

    private Preferences validatePreference()
    {
        PreferencesDao prefDao    = new PreferencesDao(mainActivity);
        Preferences preferences   = prefDao.getPreferences();

        if (preferences == null)
        {
            showPreferenceNotFoundDialog();
            return null;
        }
        else if (preferences.getProvinceCode() == null)
        {
            showPreferenceNotFoundDialog();
            return null;
        }

        return preferences;
    }

    private Province validateProvince(Preferences preferences)
    {
        ProvincesDao provincesDao = new ProvincesDao(mainActivity);
        Province province = provincesDao.selectByCode(preferences.getProvinceCode());

        if (province == null)
        {
            showProvinceNotFoundDialog();
            return null;
        }
        else if (province.getGst() == null || province.getPst() == null)
        {
            showProvinceNotFoundDialog();
            return null;
        }

        return province;
    }

    /****************************************************************************************/
    // Dialogs
    /****************************************************************************************/

    private void showPreferenceNotFoundDialog()
    {
        String title = getResources().getString(R.string.msg_dialog_title_alert);
        String msg  = getResources().getString(R.string.msg_dialog_no_preference_found);

        AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void showProvinceNotFoundDialog()
    {
        String title = getResources().getString(R.string.msg_dialog_title_alert);
        String msg  = getResources().getString(R.string.msg_dialog_no_province_found);
        showAlertDialog(title, msg);
    }

    private void showCurrencyRateNotFoundDialog()
    {
        String title = getResources().getString(R.string.msg_dialog_title_alert);
        String msg  = getResources().getString(R.string.msg_dialog_no_currency_rate_found);
        showAlertDialog(title, msg);
    }

    public void showAlertDialog(String title, String msg)
    {
        AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showWishListDialog()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
        View wishlistDialogView       = layoutInflater.inflate(R.layout.dialog_wishlist_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);

        dialogName  = (EditText) wishlistDialogView.findViewById(R.id.dialog_wishlist_name);
        dialogStore = (EditText) wishlistDialogView.findViewById(R.id.dialog_wishlist_store);

        alertDialogBuilder.setView(wishlistDialogView);
        alertDialogBuilder.setTitle(messageHelper.getWishListDialogTitle());

        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                saveToWishList();
            }
        });

        alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
