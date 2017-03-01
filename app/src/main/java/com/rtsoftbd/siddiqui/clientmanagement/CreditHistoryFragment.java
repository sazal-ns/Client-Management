package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreditHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreditHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.list) ListView listView;
    @BindView(R.id.totalBalanceTextView) TextView totalBalanceTextView;

    private ProgressDialog progressDialog;
    private List<Credit> credits = new ArrayList<>();
    private CustomListAdapter customListAdapter;
    private int totalCredit;

    public CreditHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditHistoryFragment newInstance(String param1, String param2) {
        CreditHistoryFragment fragment = new CreditHistoryFragment();
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


        View view = inflater.inflate(R.layout.fragment_credit_history, container, false);
        ButterKnife.bind(this,view);

        customListAdapter = new CustomListAdapter(getActivity(), credits, false);
        listView.setAdapter(customListAdapter);

        totalCredit =0;
        showList();


        return view;
    }

    private void showList() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.pleaseWait));
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.CREDIT_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").contentEquals("false")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("creditHistory");

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

                            totalCredit+=credit.getMixBalance();
                            credits.add(credit);
                        }
                        customListAdapter.notifyDataSetChanged();
                        totalBalanceTextView.setText(getResources().getString(R.string.totalCredit)+" " + totalCredit);
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
                    new ShowDialog(getContext(), null, getResources().getString(R.string.noInternet),true,null);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromDate", "02/01/2017");
                params.put("toDate", "02/28/2017");
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);

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
