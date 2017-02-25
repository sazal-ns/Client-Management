package com.rtsoftbd.siddiqui.clientmanagement.apiHandeler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ApiUrl;
import com.rtsoftbd.siddiqui.clientmanagement.helper.CustomVolleyRequestQueue;
import com.rtsoftbd.siddiqui.clientmanagement.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-23.
 */

public class Login extends AsyncTask<Void, Void, Void> {

    private String userName, password;
    private Context context;
    private User user;
    private RequestQueue mQueue;

    private boolean check;

    public Login(String userName, String password, Context context) {
        this.userName = userName;
        this.password = password;
        this.context = context;
        setCheck(false);
        user = new User();
    }

    public void doLogin() {

    }

    public boolean isCheck() {
        return check;
    }

    private void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    protected Void doInBackground(Void... params) {

        mQueue = CustomVolleyRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.login, new Response.Listener<String>() {
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

                        setCheck(true);
                    }


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
                params.put("name", userName);
                params.put("password", password);

                return params;
            }
        };

        request.setTag("LoginActivity");
        mQueue.add(request);

        return null;
    }
}
