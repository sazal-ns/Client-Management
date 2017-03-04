package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtsoftbd.siddiqui.clientmanagement.adapter.CustomListAdapter;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ApiUrl;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ShowDialog;
import com.rtsoftbd.siddiqui.clientmanagement.model.Credit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class LedgarActivity extends AppCompatActivity {

    @BindView(R.id.head) LinearLayout head;
    @BindView(R.id.fromDateEditText) EditText fromDateEditText;
    @BindView(R.id.toDateEditText) EditText toDateEditText;
    @BindView(R.id.nameTextView) TextView nameTextView;
    @BindView(R.id.totalBalanceTextView) TextView totalBalanceTextView;
    @BindView(R.id.totalCreditTextView) TextView totalCreditTextView;
    @BindView(R.id.totalPaidTextView) TextView totalPaidTextView;
    @BindView(R.id.list) ListView listView;
    @BindView(R.id.a) TextInputLayout a;
    @BindView(R.id.b) TextInputLayout b;
    @BindView(R.id.btn_setDate) Button setDate;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.below) LinearLayout below;

    private Intent intent;

    private Calendar calendar= Calendar.getInstance();

    String toDate, fromDate;
    int one=0, two=0;

    private int totalCredit, totalDebit, totalBalance;
    private String id;

    private ProgressDialog progressDialog;
    private List<Credit> credits = new ArrayList<>();
    private CustomListAdapter customListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledgar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTextView.setVisibility(View.GONE);

        intent = getIntent();
        id = String.valueOf(intent.getIntExtra("id",0));
        getSupportActionBar().setTitle(intent.getStringExtra("name")+ getResources().getString(R.string.s_ledger));

        totalCredit = totalDebit = totalBalance = 0;

        fromDateEditText.setVisibility(View.GONE);
        toDateEditText.setVisibility(View.GONE);
        setDate.setVisibility(View.GONE);

        customListAdapter = new CustomListAdapter(this,credits,true,false);
        listView.setAdapter(customListAdapter);

        showList("02/01/2017", "02/05/2017", ApiUrl.LEDGAR);

    }

    private void showList(final String fromDate, final String toDate, String url) {
        progressDialog = new ProgressDialog(LedgarActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.pleaseWait));
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").contentEquals("false")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("clientBlance");

                        for (int i = 0; i< jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject( i);
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

                            totalCredit+=credit.getCredit();
                            totalDebit+=credit.getDebit();
                            totalBalance= totalCredit - totalDebit;
                            credits.add(credit);
                        }
                        customListAdapter.notifyDataSetChanged();
                        listView.setVisibility(View.VISIBLE);
                        totalPaidTextView.setText(getResources().getString(R.string.totalPaid)+" " + totalDebit);
                        totalCreditTextView.setText(getResources().getString(R.string.totalCredit)+" "+totalCredit);
                        totalBalanceTextView.setText(getResources().getString(R.string.balance)+" "+totalBalance);
                    }else{
                        Log.e("else",response);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Error", error.toString());
                if (error.toString().contains("NoConnectionError")){
                    new ShowDialog(LedgarActivity.this, null, getResources().getString(R.string.noInternet),true,null);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromDate", fromDate);
                params.put("toDate", toDate);
                params.put("id",id);
                return params;
            }
        };

        Volley.newRequestQueue(LedgarActivity.this).add(request);

    }

    private void searchByDate(){
        credits.clear();
        totalCredit = totalDebit = totalBalance =0;
        listView.setVisibility(View.GONE);
        head.setVisibility(View.GONE);
        below.setVisibility(View.GONE);

        setDate.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        fromDateEditText.setVisibility(View.VISIBLE);
        toDateEditText.setVisibility(View.VISIBLE);

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LedgarActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                one=1;
            }
        });

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LedgarActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                two = 1;
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onValidateFailed();
                    return;
                }

                fromDateEditText.setVisibility(GONE);
                a.setVisibility(View.GONE);
                toDateEditText.setVisibility(GONE);
                b.setVisibility(View.GONE);
                setDate.setVisibility(GONE);


                head.setVisibility(View.VISIBLE);
                //listView.setVisibility(View.VISIBLE);
                below.setVisibility(View.VISIBLE);

                showList(fromDate,toDate, ApiUrl.LEGGER_BY_DATE);
            }
        });
    }

    private void onValidateFailed() {

    }

    private boolean validate() {
        boolean valid = true;

        String email = toDateEditText.getText().toString();
        String password = fromDateEditText.getText().toString();

        if (email.isEmpty()) {
            toDateEditText.setError(getResources().getString(R.string.setDate));
            valid = false;
        } else {
            toDateEditText.setError(null);
        }

        if (password.isEmpty()) {
            fromDateEditText.setError(getResources().getString(R.string.setDate));
            valid = false;
        } else {
            fromDateEditText.setError(null);
        }

        return valid;
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (one==1){
                toDateEditText.setText(formatDate(calendar.getTime()));
                toDate = formatDate(calendar.getTime());
                one=2;
            }
            if (two==1){
                fromDateEditText.setText(formatDate(calendar.getTime()));
                fromDate = formatDate(calendar.getTime());
                two=2;
            }
        }
    };

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String hireDate = sdf.format(date);
        return hireDate;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_search:
                searchByDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
