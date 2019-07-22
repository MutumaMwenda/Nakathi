package com.impax.nakathisacco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.CertsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class Tab3Fragment extends Fragment {


    Session session;
    String id_number;
    private RecyclerView recyclerView;
    private List<CertsModel> certsModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String loan_id,amount;
    INakathiAPI mService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_tab_three, container, false);
        mService = Common.getAPI();
        session = new Session(getActivity());
        loan_id=session.getLoanId();
        id_number = session.getIdNumber();
        amount=session.getAmount();
        getCerts(id_number);
        recyclerView = rootView.findViewById(R.id.rvCerts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CertsAdapter(getActivity(),certsModels);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private  void getCerts(String id_number){
        mService.getCerts(id_number).enqueue(new Callback<List<CertsModel>>() {
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
                Toast.makeText(getActivity(), "Error occurred while processing your request", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
