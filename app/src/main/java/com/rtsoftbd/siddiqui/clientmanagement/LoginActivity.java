package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ApiUrl;
import com.rtsoftbd.siddiqui.clientmanagement.helper.CustomVolleyRequestQueue;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ShowDialog;
import com.rtsoftbd.siddiqui.clientmanagement.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;

    private User user;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onValidateFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.authenticating));
        progressDialog.show();

        final String email = _emailText.getText().toString().trim();
        final String password = _passwordText.getText().toString().trim();


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                user = new User();
                mQueue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
            }

            @Override
            protected Void doInBackground(Void... params) {

                StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("doLogin onResponse", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("error").contentEquals("false")){
                                JSONObject object = jsonObject.getJSONObject("user");
                                Log.d("doLogin onResponse", object.toString());

                                user.setId(object.getInt("id"));
                                user.setName(object.getString("name"));
                                user.setEmail(object.getString("email"));
                                user.setMobile(object.getString("mobile"));
                                user.setStatus(object.getInt("status"));
                                user.setCredit(object.getInt("credit"));
                                user.setDebit(object.getInt("debit"));
                                user.setBalance(object.getInt("balance"));
                                user.setDescription(object.getString("description"));
                                user.setPermission(object.getInt("permission"));
                                user.setCreated_at(object.getString("created_at"));
                                user.setUpdated_at(object.getString("updated_at"));
                                user.setLoginDate(object.getString("loginDate"));

                                onLoginSuccess();
                            }else onLoginFailed();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", email);
                        params.put("password", password);

                        return params;
                    }
                };

                request.setTag("LoginActivity");
                mQueue.add(request);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.hide();
            }
        }.execute();

    }


    private void onValidateFailed() {
        _loginButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        _loginButton.setEnabled(true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void onLoginFailed() {
        new ShowDialog(LoginActivity.this, getResources().getString(R.string.error),
                getResources().getString(R.string.loginFailed),getResources().getDrawable(R.drawable.ic_warning_red_24dp));

        _loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError(getResources().getString(R.string.enterUserName));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError(getResources().getString(R.string.passwordError));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(TAG);
        }
    }
}
