package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangePasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.userNameEditText) EditText userNameEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText confirmPasswordEdtiText;
    @BindView(R.id.changePasswordButton) Button changePasswordButton;

    private String password, confirmPassword;

    private ProgressDialog progressDialog;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);

        userNameEditText.setKeyListener(null);
        userNameEditText.setText(User.getName());

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = passwordEditText.getText().toString();
                confirmPassword = confirmPasswordEdtiText.getText().toString();

                changePasswordButton.setEnabled(false);


                changePassword();
            }
        });


        return view;
    }

    private void changePassword() {
        if (!validate()) {
            onValidateFailed();
            return;
        }

        changePasswordButton.setEnabled(false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.pleaseWait));
        progressDialog.show();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {

                StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.CHANGE_PASSWORD, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        changePasswordButton.setEnabled(true);
                        Log.e("onResponse", response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            if (jsonObject.getString("error").contentEquals("false")) {
                                new ShowDialog(getContext(), getResources().getString(R.string.success),
                                        getResources().getString(R.string.successfullyAccomplished),true,
                                        getResources().getDrawable(R.drawable.ic_done_all_green_a700_24dp));
                            }else  new ShowDialog(getContext(), getResources().getString(R.string.error),
                                    getResources().getString(R.string.serverSays)+"\n"+ getResources().getString(R.string.error),true,
                                    getResources().getDrawable(R.drawable.ic_error_red_a700_24dp));
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
                            new ShowDialog(getContext(), null, getResources().getString(R.string.noInternet),true,null);
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("password", password);
                        params.put("confirmPassword", confirmPassword);
                        params.put("id", String.valueOf(User.getId()));

                        return params;
                    }
                };

                Volley.newRequestQueue(getContext()).add(request);

                return null;
            }
        }.execute();


    }

    private void onValidateFailed() {
        changePasswordButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;


        if (password.isEmpty() || password.length() < 6 || password.length() > 50){
            passwordEditText.setError(getResources().getString(R.string.passwordError));
            valid = false;
        }else passwordEditText.setError(null);

        if (confirmPassword.isEmpty() || !confirmPassword.contentEquals(password)){
            confirmPasswordEdtiText.setError(getResources().getString(R.string.confromPassword));
            valid = false;
        }else confirmPasswordEdtiText.setError(null);


        return valid;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
