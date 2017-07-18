package com.calixinteractive.taxcalculator.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.calixinteractive.taxcalculator.MainActivity;
import com.calixinteractive.taxcalculator.R;
import com.calixinteractive.taxcalculator.adapter.WishListAdapter;
import com.calixinteractive.taxcalculator.dao.WishListDao;
import com.calixinteractive.taxcalculator.helper.MessageHelper;
import com.calixinteractive.taxcalculator.model.WishList;

import java.util.ArrayList;

/**
 * Created by maylcf on 12/07/2017.
 */

public class WishListFragment extends Fragment
{
    private View view;
    private MainActivity mainActivity;
    private TextView txtMsg;
    private ListView listView;
    private ArrayList<WishList> arrayOfWishList;
    private WishListDao wishListDao;
    private WishListAdapter adapter;
    private MessageHelper messageHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        /****************************************************************************************/
        // Get View Components

        listView = (ListView) view.findViewById(R.id.frag_wish_list_listview);
        txtMsg   = (TextView) view.findViewById(R.id.frag_wish_list_msg);

        /****************************************************************************************/
        // Get Other Components

        mainActivity     = (MainActivity) getActivity();
        wishListDao      = new WishListDao(mainActivity);
        arrayOfWishList  = new ArrayList<WishList>();
        messageHelper    = new MessageHelper(mainActivity);

        /****************************************************************************************/
        // Set Title

        mainActivity.setTitle(messageHelper.getWishListFragmentTitle());

        /****************************************************************************************/
        // Load Screen

        loadScreen();

        /****************************************************************************************/
        // ListView LongClick - Open Delete Wish List Dialog

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id)
            {
                WishList wishList = adapter.getItem(pos);
                showDeleteDialog(wishList);

                return true;
            }
        });

        /****************************************************************************************/
        // Return View

        return view;
    }

    @Override
    public void onResume()
    {
        loadScreen();
        super.onResume();
    }

    private void loadScreen()
    {
        // Update List

        arrayOfWishList = wishListDao.selectAll();

        if (!arrayOfWishList.isEmpty())
        {
            adapter = new WishListAdapter(mainActivity, arrayOfWishList);
            listView.setAdapter(adapter);
        }

        // Display Msg If List is Empty

        if (arrayOfWishList.isEmpty())
        {
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            txtMsg.setLayoutParams(params);
            txtMsg.setVisibility(View.VISIBLE);
            txtMsg.setText(R.string.msg_no_item_found);
        }
        else
        {
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, 0);
            txtMsg.setLayoutParams(params);
            txtMsg.setText("");
            txtMsg.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteWishList(WishList wishList)
    {
        wishListDao.delete(wishList);
        Toast.makeText(mainActivity, wishList.getName() + " " + messageHelper.getSuccessDeleteMsg(), Toast.LENGTH_SHORT).show();
        onResume();
    }

    private void showDeleteDialog(final WishList wishList)
    {
        AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setTitle(messageHelper.getDeleteDialogTitle());
        alertDialogBuilder.setMessage(wishList.getName() + " " + messageHelper.getConfirmDeleteMessage());

        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                deleteWishList(wishList);
            }
        });

        alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
