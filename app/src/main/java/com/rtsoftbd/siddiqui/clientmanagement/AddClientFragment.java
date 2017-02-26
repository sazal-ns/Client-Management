package com.rtsoftbd.siddiqui.clientmanagement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
                addClient();
            }
        });

        return view;
    }

    private void addClient() {
        if (!validate()) {
            onValidateFailed();
            return;
        }
    }

    private void onValidateFailed() {

    }

    private boolean validate() {
        boolean valid = true;



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
