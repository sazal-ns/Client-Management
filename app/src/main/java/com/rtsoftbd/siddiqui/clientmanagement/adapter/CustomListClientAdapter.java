package com.rtsoftbd.siddiqui.clientmanagement.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtsoftbd.siddiqui.clientmanagement.R;
import com.rtsoftbd.siddiqui.clientmanagement.model.AllUser;
import com.rtsoftbd.siddiqui.clientmanagement.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RTsoftBD_Siddiqui on 2017-03-01.
 */

public class CustomListClientAdapter extends BaseAdapter {

    private FragmentActivity activity;
    private LayoutInflater layoutInflater;
    private List<AllUser> users;

    @BindView(R.id.nameTextViewRow)TextView nameTextView;
    @BindView(R.id.mobileTextViewRow) TextView mobileTextView;
    @BindView(R.id.balanceTextViewRow) TextView balanceTextView;
    @BindView(R.id.lastLoginTextViewRow) TextView lastLoginTextView;
    @BindView(R.id.createdTextViewRow) TextView createdTextView;
    @BindView(R.id.statusImageViewRow) ImageView statusImageViewRow;

    public CustomListClientAdapter(FragmentActivity activity, List<AllUser> users) {
        this.activity = activity;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null) layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = layoutInflater.inflate(R.layout.list_client, null);
        ButterKnife.bind(this,convertView);

        AllUser user = users.get(position);

        nameTextView.setText(user.getName());
        mobileTextView.setText(user.getMobile());
        balanceTextView.setText(String.valueOf(user.getBalance()));
        lastLoginTextView.setText(user.getLoginDate());
        createdTextView.setText(user.getCreated_at());
        if (user.getStatus()==1) statusImageViewRow.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_check_green_a700_24dp));
        else statusImageViewRow.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_close_red_a700_24dp));


        return convertView;
    }
}
