package com.calixinteractive.taxcalculator.helper;

import android.content.Context;

import com.calixinteractive.taxcalculator.R;

/**
 * Created by mayca on 17/07/2017.
 */

public class MessageHelper
{
    private Context context;

    public MessageHelper(Context c)
    {
        this.context = c;
    }

    /*********************************************************************************************/
    // Titles
    /*********************************************************************************************/

    public String getExchangeFragmentTitle()
    {
        return this.context.getResources().getString(R.string.title_exchange);
    }

    public String getWishListFragmentTitle()
    {
        return this.context.getResources().getString(R.string.title_wish_list);
    }

    public String getCurrencyRateFragmentTitle()
    {
        return this.context.getResources().getString(R.string.title_currency_rate);
    }

    public String getPreferencesFragmentTitle()
    {
        return this.context.getResources().getString(R.string.title_preferences);
    }

    /*********************************************************************************************/
    // Dialogs
    /*********************************************************************************************/

    public String getProvinceDialogTitle()
    {
        return this.context.getResources().getString(R.string.preferences_form_select_province);
    }


    public String getDeleteDialogTitle()
    {
        return this.context.getResources().getString(R.string.msg_dialog_title_delete);
    }

    public String getConfirmDeleteMessage()
    {
        return this.context.getResources().getString(R.string.msg_dialog_wishlist_delete);
    }

    /*********************************************************************************************/
    // Return
    /*********************************************************************************************/

    public String getSuccessUpdateMsg()
    {
        return this.context.getResources().getString(R.string.msg_update);
    }

    public String getSuccessDeleteMsg()
    {
        return this.context.getResources().getString(R.string.msg_delete);
    }


    public String getManualInputFont()
    {
        return this.context.getResources().getString(R.string.currency_rate_form_font_manual);
    }

    public String getManualInputCode()
    {
        return this.context.getResources().getString(R.string.default_manual_input_code);
    }

}
