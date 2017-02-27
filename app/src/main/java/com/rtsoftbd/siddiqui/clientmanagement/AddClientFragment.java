package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ApiUrl;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ShowDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.userNameEditText) EditText userNameEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText confirmPasswordEditText;
    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.mobileEditText) EditText mobileEditText;
    @BindView(R.id.descriptionEditText) EditText descriptionEditText;
    @BindView(R.id.statusSpinner) Spinner statusSpinner;
    @BindView(R.id.addClientButton) Button addClientButton;

    private String userName, password, confirmPassword, email, mobile, description;
    private int status;
    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClientFragment newInstance(String param1, String param2) {
        AddClientFragment fragment = new AddClientFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_client, container, false);
        ButterKnife.bind(this, view);

        addClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                confirmPassword = confirmPasswordEditText.getText().toString();
                email = emailEditText.getText().toString();
                mobile = mobileEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                addClient();
            }
        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1) status =0;
                else status = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void addClient() {
        if (!validate()) {
            onValidateFailed();
            return;
        }

        addClientButton.setEnabled(false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.pleaseWait));
        progressDialog.show();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.ADD_CLIENT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        progressDialog.hide();

                        addClientButton.setEnabled(true);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("error").contentEquals("false")){
                                new ShowDialog(getContext(), getResources().getString(R.string.success),
                                        getResources().getString(R.string.successfullyAccomplished),
                                        getResources().getDrawable(R.drawable.ic_done_all_green_a700_24dp));
                            }else{
                                JSONObject object = jsonObject.getJSONObject("message");
                                JSONArray array = object.getJSONArray("name");
                                new ShowDialog(getContext(), getResources().getString(R.string.error),
                                        getResources().getString(R.string.serverSays)+"\n"+array.toString(),
                                        getResources().getDrawable(R.drawable.ic_error_red_a700_24dp));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name",userName);
                        params.put("email",email);
                        params.put("password",password);
                        params.put("confirmPassword",confirmPassword);
                        params.put("mobile",mobile);
                        params.put("status", String.valueOf(status));
                        params.put("description",description);

                        return params;
                    }
                };

                Volley.newRequestQueue(getContext()).add(request);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //progressDialog.hide();
            }
        }.execute();


    }

    private void onValidateFailed() {
        addClientButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        if (userName.isEmpty() || userName.length()<3 && userName.length()>50){
            userNameEditText.setError(getResources().getString(R.string.enterUserName));
            valid = false;
        }else userNameEditText.setError(null);

        if (password.isEmpty() || password.length() < 6 && password.length() > 50){
            passwordEditText.setError(getResources().getString(R.string.passwordError));
            valid = false;
        }else passwordEditText.setError(null);

        if (confirmPassword.isEmpty() || !confirmPassword.contentEquals(password)){
            confirmPasswordEditText.setError(getResources().getString(R.string.confromPassword));
            valid = false;
        }else confirmPasswordEditText.setError(null);


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
        progressDialog.dismiss();
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
