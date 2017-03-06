package com.rtsoftbd.siddiqui.clientmanagement.helper;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.rtsoftbd.siddiqui.clientmanagement.R;
import com.rtsoftbd.siddiqui.clientmanagement.adapter.CustomListAdapterIndex;
import com.rtsoftbd.siddiqui.clientmanagement.model.Credit;
import com.rtsoftbd.siddiqui.clientmanagement.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private Credit credit;
    private final List<ItemModel> data;
    private Context context;
    private int who;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    private ProgressDialog progressDialog;
    private List<Credit> credits = new ArrayList<>();
    private List<Credit> paids = new ArrayList<>();
    private CustomListAdapterIndex customListAdapter;
    private CustomListAdapterIndex customListAdapterPaid;
    private Activity activity;


    public RecyclerViewRecyclerAdapter(final List<ItemModel> data, int who, Activity activity) {
        this.data = data;
        this.who = who;
        this.activity = activity;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
        credit = new Credit();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_list_row, parent, false));

        customListAdapter = new CustomListAdapterIndex(activity, credits);
        holder.creditListView.setAdapter(customListAdapter);
        customListAdapterPaid = new CustomListAdapterIndex(activity, paids);
        holder.paidListView.setAdapter(customListAdapterPaid);

        loadList(holder);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getResources().getString(R.string.pleaseWait));
        //progressDialog.show();

        return holder;
    }

    private void loadList(final ViewHolder holder) {
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.INDEX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").contentEquals("false")) {

                        if (who==1) {
                            credit.setTotalCredit(jsonObject.getInt("totalCredit"));
                            credit.setTotalPaid(jsonObject.getInt("totalPaid"));
                            }else {
                            credit.setTotalCredit(jsonObject.getInt("totalCreditById"));
                            credit.setTotalPaid(jsonObject.getInt("totalPaidById"));
                            }
                            Log.e("check ", String.valueOf(credit.getTotalPaid()));
                        holder.totalPaidTextView.setText(context.getResources().getString(R.string.totalPaid) + "\t" + String.valueOf(credit.getTotalPaid()));
                        holder.totalCreditTextView.setText(context.getResources().getString(R.string.totalCredit) + "\t" + String.valueOf(credit.getTotalCredit()));
                        holder.totalBalanceTextView.setText(context.getResources().getString(R.string.balance) + "\t" + String.valueOf(credit.getTotalCredit() - credit.getTotalPaid()));
                        JSONArray jsonArray=null;
                        if (who==1) {
                            jsonArray = jsonObject.getJSONArray("allcredit");
                        }else {
                            jsonArray = jsonObject.getJSONArray("creditById");
                        }
                        for (int i = 0; i< jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Credit credit = new Credit();
                            credit.setId(object.getInt("id"));
                            credit.setName(object.getString("name"));
                            credit.setCredit(object.getInt("credit"));
                            credit.setDebit(object.getInt("debit"));
                            credit.setMixBalance(object.getInt("mixBalance"));
                            credit.setRecentBalance(object.getInt("recentBalance"));
                            credit.setChildId(object.getInt("childId"));
                            credit.setType(object.getString("type"));
                            credit.setDescription(object.getString("description"));
                            credit.setDate(object.getString("date"));
                            credit.setReportDate(object.getString("reportDate"));

                            credits.add(credit);
                        }
                        customListAdapter.notifyDataSetChanged();

                        JSONArray jsonArrayPaid=null;
                        if (who==1) {
                            jsonArrayPaid = jsonObject.getJSONArray("allPaid");
                        }else {
                            jsonArrayPaid = jsonObject.getJSONArray("paidById");
                        }

                        for (int i = 0; i< jsonArrayPaid.length(); i++){
                            JSONObject object = jsonArrayPaid.getJSONObject(i);
                            Credit credit = new Credit();
                            credit.setId(object.getInt("id"));
                            credit.setName(object.getString("name"));
                            credit.setCredit(object.getInt("credit"));
                            credit.setDebit(object.getInt("debit"));
                            credit.setMixBalance(object.getInt("mixBalance"));
                            credit.setRecentBalance(object.getInt("recentBalance"));
                            credit.setChildId(object.getInt("childId"));
                            credit.setType(object.getString("type"));
                            credit.setDescription(object.getString("description"));
                            credit.setDate(object.getString("date"));
                            credit.setReportDate(object.getString("reportDate"));

                            paids.add(credit);
                        }
                        customListAdapterPaid.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }else{
                        Log.e("else",response);
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Error", error.toString());
                if (error.toString().contains("NoConnectionError")){
                    new ShowDialog(context.getApplicationContext(), null, context.getResources().getString(R.string.noInternet),true,null);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(User.getId()));
                return params;
            }
        };

        Volley.newRequestQueue(context.getApplicationContext()).add(request);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemModel item = data.get(position);
        holder.textView.setText(item.description);
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, item.colorId1));
        holder.expandableLayout.setInRecyclerView(true);
        holder.setIsRecyclable(true);
        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context, item.colorId2));
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
                if (position!=0){
                    holder.totalBalanceTextView.setVisibility(View.GONE);
                    holder.totalCreditTextView.setVisibility(View.GONE);
                    holder.totalPaidTextView.setVisibility(View.GONE);
                }
                if (position!=1){
                    holder.paidListView.setVisibility(View.GONE);
                }
                if (position!=2){
                    holder.creditListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, totalPaidTextView, totalCreditTextView, totalBalanceTextView;
        RelativeLayout buttonLayout;
        ListView paidListView, creditListView;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        ExpandableLinearLayout expandableLayout;

        ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.summeryExpandableLayout);
            totalPaidTextView = (TextView) v.findViewById(R.id.totalPaidTextView);
            totalCreditTextView = (TextView) v.findViewById(R.id.totalCreditTextView);
            totalBalanceTextView = (TextView) v.findViewById(R.id.totalBalanceTextView);
            paidListView = (ListView) v.findViewById(R.id.paidListView);
            creditListView = (ListView) v.findViewById(R.id.creditListView);

        }
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }


}