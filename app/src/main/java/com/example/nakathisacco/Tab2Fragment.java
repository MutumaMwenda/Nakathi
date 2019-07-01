package com.example.nakathisacco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nakathisacco.Model.CertsModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.CertsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class Tab2Fragment extends Fragment {


    Session session;
    String id_number,reg_no;
    private RecyclerView recyclerView;
    private List<CertsModel> certsModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String loan_id,amount;
    INakathiAPI mService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_tab_two, container, false);
        mService = Common.getAPI();
        session = new Session(getActivity());
        loan_id=session.getLoanId();
        id_number = session.getIdNumber();
        reg_no="KCJ 098K";
        amount=session.getAmount();
        getVehicleCerts(reg_no);
        recyclerView = rootView.findViewById(R.id.rvCerts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CertsAdapter(getActivity(),certsModels);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private  void getVehicleCerts(String reg_no){
        mService.getVehicleCerts(reg_no).enqueue(new Callback<List<CertsModel>>() {
            @Override
            public void onResponse(Call<List<CertsModel>> call, Response<List<CertsModel>> response) {

                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                    Toast.makeText(getActivity(), "No Certifications Available", Toast.LENGTH_SHORT).show();
                } else {

                    certsModels.clear();
                    certsModels.addAll(response.body());
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(Call<List<CertsModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(getActivity(), "Error occured while processing your request", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
