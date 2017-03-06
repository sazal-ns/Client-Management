package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rtsoftbd.siddiqui.clientmanagement.model.User;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    @BindView(R.id.below) LinearLayout below;

    private Calendar calendar= Calendar.getInstance();

    String toDate, fromDate;
    int one=0, two=0;

    private int totalCredit, totalDebit, totalBalance;

    private ProgressDialog progressDialog;
    private List<Credit> credits = new ArrayList<>();
    private CustomListAdapter customListAdapter;

    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance(String param1, String param2) {
        ClientFragment fragment = new ClientFragment();
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
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        ButterKnife.bind(this, view);

        nameTextView.setVisibility(View.GONE);

        totalCredit = totalDebit = totalBalance = 0;

        fromDateEditText.setVisibility(View.GONE);
        toDateEditText.setVisibility(View.GONE);
        setDate.setVisibility(View.GONE);

        customListAdapter = new CustomListAdapter(getActivity(),credits,true,false);
        listView.setAdapter(customListAdapter);

        showList("02/01/2017", "02/05/2017", ApiUrl.LEDGAR);

        return view;
    }

    private void showList(final String fromDate, final String toDate, String url) {
        progressDialog = new ProgressDialog(getContext());
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
                    new ShowDialog(getContext(), null, getResources().getString(R.string.noInternet),true,null);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromDate", fromDate);
                params.put("toDate", toDate);
                params.put("id", String.valueOf(User.getId()));
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);

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
                new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                one=1;
            }
        });

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR),
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
