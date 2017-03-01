package com.rtsoftbd.siddiqui.clientmanagement.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rtsoftbd.siddiqui.clientmanagement.R;
import com.rtsoftbd.siddiqui.clientmanagement.model.Credit;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-28.
 */

public class CustomListAdapter extends BaseAdapter {

    private FragmentActivity activity;
    private LayoutInflater layoutInflater;
    private Boolean isTotal;

    private List<Credit> credits;

    public CustomListAdapter(FragmentActivity activity, List<Credit> credits, Boolean isTotal) {
        this.activity = activity;
        this.credits = credits;
        this.isTotal = isTotal;
    }


    @Override
    public int getCount() {
        return credits.size();
    }

    @Override
    public Object getItem(int position) {
        return credits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_row, null);

        TextView timeDate = (TextView) convertView.findViewById(R.id.timeDateTextViewRow);
        TextView name = (TextView) convertView.findViewById(R.id.nameTextViewRow);
        TextView creditPayment = (TextView) convertView.findViewById(R.id.creditPaymentTextViewRow);
        TextView balance = (TextView) convertView.findViewById(R.id.balanceTextViewRow);
        TextView debitPayment = (TextView) convertView.findViewById(R.id.debitPaymentTextViewRow);

        Credit c = credits.get(position);

        timeDate.setText(c.getDate());
        name.setText(c.getName());
        creditPayment.setText(String.valueOf(c.getMixBalance()));
        balance.setText(String.valueOf(c.getRecentBalance()));
        if (isTotal){
            creditPayment.setText(String.valueOf(c.getCredit()));
            debitPayment.setText(String.valueOf(c.getDebit()));
        }
        else {
            debitPayment.setVisibility(View.GONE);
        }
        return convertView;
    }
}
