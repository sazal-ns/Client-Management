package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ApiUrl;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ShowDialog;
import com.rtsoftbd.siddiqui.clientmanagement.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditClientActivity extends AppCompatActivity {

    @BindView(R.id.userNameEditText) EditText userNameEditText;
    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.phoneEditText) EditText phoneEditText;
    @BindView(R.id.descriptionEditText) EditText descriptionEditText;
    @BindView(R.id.saveButton) Button saveButton;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private Intent intent;

    private String id;

    private String userName, email, phone, description;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        id = String.valueOf(intent.getIntExtra("id",0));
        getSupportActionBar().setTitle(intent.getStringExtra("name")+getResources().getString(R.string.s_profile));

        userNameEditText.setText(intent.getStringExtra("name"));
        emailEditText.setText(intent.getStringExtra("email"));
        phoneEditText.setText(intent.getStringExtra("phone"));
        descriptionEditText.setText(intent.getStringExtra("dec"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = userNameEditText.getText().toString();
                email = emailEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                editClient(id);
            }
        });



    }

    private void editClient(final String id) {
        if (!validate()) {
            onValidateFailed();
            return;
        }

        saveButton.setEnabled(false);

        progressDialog = new ProgressDialog(EditClientActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.pleaseWait));
        progressDialog.show();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.EDIT_CLIENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        saveButton.setEnabled(true);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("error").contentEquals("false")){
                                getSupportActionBar().setTitle(userName+getResources().getString(R.string.s_profile));
                                new ShowDialog(EditClientActivity.this, getResources().getString(R.string.success),
                                        getResources().getString(R.string.successfullyAccomplished),true,
                                        getResources().getDrawable(R.drawable.ic_done_all_green_a700_24dp));
                            }else{
                                JSONObject object = jsonObject.getJSONObject("message");

                                try{
                                    JSONArray array = object.getJSONArray("mobile");
                                    new ShowDialog(EditClientActivity.this, getResources().getString(R.string.error),
                                            getResources().getString(R.string.serverSays)+"\n"+array.toString(),true,
                                            getResources().getDrawable(R.drawable.ic_error_red_a700_24dp));

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                                try{
                                    JSONArray array = object.getJSONArray("email");
                                    new ShowDialog(EditClientActivity.this, getResources().getString(R.string.error),
                                            getResources().getString(R.string.serverSays)+"\n"+array.toString(),true,
                                            getResources().getDrawable(R.drawable.ic_error_red_a700_24dp));

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                                try{
                                    JSONArray array = object.getJSONArray("name");
                                    new ShowDialog(EditClientActivity.this, getResources().getString(R.string.error),
                                            getResources().getString(R.string.serverSays)+"\n"+array.toString(),true,
                                            getResources().getDrawable(R.drawable.ic_error_red_a700_24dp));

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
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
                            new ShowDialog(EditClientActivity.this, null, getResources().getString(R.string.noInternet),true,null);
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name",userName);
                        params.put("email",email);
                        params.put("mobile",phone);
                        params.put("id",id);
                        params.put("description",description);

                        return params;
                    }
                };

                Volley.newRequestQueue(EditClientActivity.this).add(request);

                return null;
            }
        }.execute();
    }

    private void onValidateFailed() {
        saveButton.setEnabled(true);
    }


    private boolean validate() {
        boolean valid = true;

        if (userName.isEmpty() || userName.length() < 3 || userName.length() > 50 ) {
            userNameEditText.setError(getResources().getString(R.string.enterUserName));
            valid = false;
        } else {
            userNameEditText.setError(null);
        }

        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
