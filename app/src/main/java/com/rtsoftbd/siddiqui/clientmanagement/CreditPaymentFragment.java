package com.rtsoftbd.siddiqui.clientmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rtsoftbd.siddiqui.clientmanagement.adapter.CustomListClientAdapter;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ApiUrl;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ShowDialog;
import com.rtsoftbd.siddiqui.clientmanagement.model.AllUser;

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
 * {@link CreditPaymentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreditPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditPaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.listView)
    SwipeMenuListView swipeMenuListView;

    private ProgressDialog progressDialog;
    private List<AllUser> allUsers = new ArrayList<>();
    private CustomListClientAdapter customListClientAdapter;

    public CreditPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditPaymentFragment newInstance(String param1, String param2) {
        CreditPaymentFragment fragment = new CreditPaymentFragment();
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
        View view = inflater.inflate(R.layout.fragment_credit_payment, container, false);

        ButterKnife.bind(this,view);

        customListClientAdapter = new CustomListClientAdapter(getActivity(), allUsers, true);
        swipeMenuListView.setAdapter(customListClientAdapter);

        showList();

        return view;
    }

    private void showList() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.pleaseWait));
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, ApiUrl.CLIENT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("error").contentEquals("false")){
                        JSONArray array = jsonObject.getJSONArray("allClient");

                        for (int i= 0; i<array.length(); i++){
                            JSONObject object = array.getJSONObject(i);

                            AllUser user = new AllUser();
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

                            allUsers.add(user);
                        }
                        customListClientAdapter.notifyDataSetChanged();
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
        });

        Volley.newRequestQueue(getContext()).add(request);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle(getResources().getString(R.string.creditPayment));
                // set item title fontsize
                openItem.setTitleSize(15);
                // set item title font color
                openItem.setTitleColor(Color.BLACK);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title fontsize
                deleteItem.setTitleSize(15);
                // set item title font color
                deleteItem.setTitleColor(Color.BLACK);
                // set a icon
                deleteItem.setTitle(getResources().getString(R.string.debitPayment));
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                AllUser user = allUsers.get(position);
                switch (index){
                    case 0:
                        // open
                        creditPayment(user);
                        break;
                    case 1:
                        paidPayment(user);
                        break;
                }

                return false;
            }
        });

        swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
    }

    private void paidPayment(final AllUser user) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.debitPayment)
                .content(R.string.debitPayment)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("100", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        paid(input, user.getId());
                        progressDialog.show();
                    }
                }).show();
    }

    private void paid(final CharSequence input, final int id) {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.PAID_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(CreditPaymentFragment.this).attach(CreditPaymentFragment.this).commit();
                    allUsers.clear();
                    jsonObject = new JSONObject(response);
                    progressDialog.dismiss();
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
                params.put("id", String.valueOf(id));
                params.put("amount",input.toString());
                params.put("description","");
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void creditPayment(final AllUser user) {

        new MaterialDialog.Builder(getContext())
                .title(R.string.creditPayment)
                .content(R.string.creditPayment)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("100", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        credit(input, user.getId());
                        progressDialog.show();
                    }
                }).show();
    }

    private void credit(final CharSequence input, final int id) {

        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.CREDIT_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(CreditPaymentFragment.this).attach(CreditPaymentFragment.this).commit();
                    allUsers.clear();
                    jsonObject = new JSONObject(response);
                    progressDialog.dismiss();
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
                params.put("id", String.valueOf(id));
                params.put("amount",input.toString());
                params.put("description","");

                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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
