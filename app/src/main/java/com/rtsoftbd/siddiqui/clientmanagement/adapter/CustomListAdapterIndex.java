package com.rtsoftbd.siddiqui.clientmanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rtsoftbd.siddiqui.clientmanagement.R;
import com.rtsoftbd.siddiqui.clientmanagement.model.Credit;

import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-28.
 */

public class CustomListAdapterIndex extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Credit> credits;

    public CustomListAdapterIndex(Activity activity, List<Credit> credits) {
        this.activity = activity;
        this.credits = credits;
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
            convertView = layoutInflater.inflate(R.layout.list_row_index, null);

        TextView timeDate = (TextView) convertView.findViewById(R.id.timeDateTextViewRow);
        TextView name = (TextView) convertView.findViewById(R.id.nameTextViewRow);
        TextView balance = (TextView) convertView.findViewById(R.id.balanceTextViewRow);

        Credit c = credits.get(position);

        timeDate.setText(c.getReportDate());
        name.setText(c.getName());
        balance.setText(String.valueOf(c.getMixBalance()));

        return convertView;
    }
}
