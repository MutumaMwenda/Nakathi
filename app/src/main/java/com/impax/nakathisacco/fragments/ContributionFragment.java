package com.impax.nakathisacco.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.impax.nakathisacco.Model.LoanApplicantModel;
import com.impax.nakathisacco.Model.Vehicle;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.ContributionAdapter;
import com.impax.nakathisacco.adapters.LoansApplicantsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class ContributionFragment extends Fragment {

    Session session;
    String id_number;
    private RecyclerView recyclerView;
    private List<LoanApplicantModel> loanApplicantModels = new ArrayList<>();
    private static final String VEHICLE_ITEM_KEY = "vehicle_item_key";

    RecyclerView.Adapter adapter = null;
    private Vehicle mVehicle;

    private String loan_id,amount;
    INakathiAPI mService;

    public static ContributionFragment newInstance(String loantype, Vehicle vehicle) {
        ContributionFragment fragment = new ContributionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(VEHICLE_ITEM_KEY,vehicle);
        fragment.setArguments(bundle);



        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_contribution, container, false);
        mService = Common.getAPI();
        session = new Session(getActivity());
        loan_id=session.getLoanId();
        id_number = session.getIdNumber();
        amount=session.getAmount();
        mVehicle = getArguments().getParcelable(VEHICLE_ITEM_KEY);
        //getGuarantorLoans(id_number);
        recyclerView = rootView.findViewById(R.id.rvContribution);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ContributionAdapter(getActivity(),mVehicle.contributions);
        recyclerView.setAdapter(adapter);


        return rootView;
    }
    private void getGuarantorLoans(String id_number) {
        mService.getGuarantorLoans(id_number).enqueue(new Callback<List<LoanApplicantModel>>() {
            @Override
            public void onResponse(Call<List<LoanApplicantModel>> call, Response<List<LoanApplicantModel>> response) {
                Log.e(TAG, "onResponse: "+response.body() );


                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                  //  Toast.makeText(getActivity(), "Members not available", Toast.LENGTH_SHORT).show();
                } else {

                    loanApplicantModels.clear();
                    loanApplicantModels.addAll(response.body());
                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onFailure(Call<List<LoanApplicantModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(getActivity(), "Error occured while processing your request", Toast.LENGTH_SHORT).show();

            }
        });
    }






}
