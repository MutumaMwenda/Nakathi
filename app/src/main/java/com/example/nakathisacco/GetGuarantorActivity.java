package com.example.nakathisacco;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nakathisacco.Model.GuarantorModel;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.GuarantorCursorAdapter;
import com.example.nakathisacco.adapters.SelectGuarantorAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetGuarantorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCheckUser, btnCheckUser2, btnCheckUser3, btnSubmit;
    private EditText editTextNationalId1;
    private static final String TAG = LoanActivity.class.getSimpleName();
    // private String loan_id,amount,applicant_id,guarantor1_id;
    RecyclerView.Adapter adapter = null;
    INakathiAPI mService;
    Session session;
    private RecyclerView recyclerView;
    private List<MembersModel> membersModels = new ArrayList<>();
    private List<MembersModel> guarantorModels = new ArrayList<>();
    private Button btnAddMore;
    private SearchView searchView;
    private SimpleCursorAdapter myAdapter;
    private GuarantorCursorAdapter guarantorCursorAdapter;
    private String[] strArrData = {"No Suggestions"};
    private Cursor cursor;
    String nameOfGuarantor;
    String idnumberOfGuarantor;
    boolean flag=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_guarantor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mService = Common.getAPI();
        session = new Session(this);

        searchView = findViewById(R.id.action_search);

        final String[] from = new String[]{"name"};

        final int[] to = new int[]{android.R.id.text1};
        getMembers();


        // setup SimpleCursorAdapter
        myAdapter = new SimpleCursorAdapter(GetGuarantorActivity.this,
                android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // guarantorCursorAdapter = new GuarantorCursorAdapter(GetGuarantorActivity.this,cursor);

        SearchManager searchManager = (SearchManager) GetGuarantorActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(GetGuarantorActivity.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setSuggestionsAdapter(myAdapter);
            // Getting selected (clicked) item suggestion
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position) {
                    Log.e(TAG, "onSuggestionClick: ");

                    // Add clicked text to search box
                    CursorAdapter ca = searchView.getSuggestionsAdapter();
                    Cursor cursor = ca.getCursor();
                    cursor.moveToPosition(position);
                    nameOfGuarantor = cursor.getString(cursor.getColumnIndex("name"));
                    idnumberOfGuarantor = cursor.getString(cursor.getColumnIndex("id_number"));
                    Log.e(TAG, "Name og Guarantor " + nameOfGuarantor + " Id number " + idnumberOfGuarantor);
                    searchView.setQuery(cursor.getString(cursor.getColumnIndex("name")), false);
                    cursor.close();
                    return true;
                }

                @Override
                public boolean onSuggestionSelect(int position) {
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    Log.e(TAG, "onQueryTextChange: ");

                    //Cursor mc = getCursorFromList(membersModels);
                    //Log.e(TAG, "onQueryTextChange: "+mc );

                    //Filter data
                    final MatrixCursor mc = new MatrixCursor(new String[]{BaseColumns._ID, "name", "id_number"});
                    for (int i = 0; i < membersModels.size(); i++) {
                        if (membersModels.get(i).name.toLowerCase().startsWith(s.toLowerCase()))
                            mc.addRow(new Object[]{i, membersModels.get(i).name, membersModels.get(i).id_number});
                    }
                    myAdapter.changeCursor(mc);
                    return false;
                }
            });
        }


        membersModels.add(new MembersModel("12345678", "Aluka John", "0700234678", "johndoe@gmail.com", "4000"));
        //  membersModels.add(new MembersModel("87654321","John Doe","07893457","johndoe@gmail.com","10000"));
        // membersModels.add(new MembersModel("67845321","Ephraim Maina","089992929","johndoe@gmail.com"));
        // membersModels.add(new MembersModel("13425678","Test Account","yeeuuur","johndoe@gmail.com"));

        recyclerView = findViewById(R.id.rvGuarantors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SelectGuarantorAdapter(this, guarantorModels);
        recyclerView.setAdapter(adapter);


//       btnAddMore = findViewById(R.id.btnadd);
//       btnAddMore.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               membersModels.add(new MembersModel("12345678","Aluka John","0700234678","johndoe@gmail.com","4000"));
//               adapter.notifyDataSetChanged();
//
//           }
//       });

        btnSubmit = findViewById(R.id.btnsend);
        btnAddMore = findViewById(R.id.btnadd);


        btnSubmit.setOnClickListener(this);
        btnAddMore.setOnClickListener(this);

//        loan_id=session.getLoanId();
//        applicant_id = session.getIdNumber();
//        amount= session.getAmount();
    }


    private void getMembers() {
        mService.getMembers().enqueue(new Callback<List<MembersModel>>() {
            @Override
            public void onResponse(Call<List<MembersModel>> call, Response<List<MembersModel>> response) {

                Log.e("id", response.body().toString());
                Type type = new TypeToken<ArrayList<MembersModel>>() {
                }.getType();
                membersModels = response.body();
                ArrayList<String> dataList = new ArrayList<String>();
                Log.e(TAG, "members" + membersModels);


                for (int i = 0; i < membersModels.size(); i++) {
                    // String name = membersModel.name;
                    Log.e(TAG, "onResponse: " + membersModels.get(i));

                }
//                    strArrData = dataList.toArray(new String[dataList.size()]);
//                    Log.e(TAG, "onResponse: "+strArrData );

            }

            @Override
            public void onFailure(Call<List<MembersModel>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnadd:
                addGuarantorToAdapter();

                break;


            case R.id.btnsend:
                Log.e(TAG, "button clicked");
                submit();


                break;
        }


    }

    private void addGuarantorToAdapter() {

        if (nameOfGuarantor == null) {
            searchView.requestFocus();

        }
        if (idnumberOfGuarantor == null || idnumberOfGuarantor.length() == 0) {
            searchView.requestFocus();
            return;
        }
        searchView.setQuery("", false);
        searchView.clearFocus();
        Log.e(TAG, "button ADD clicked");
        //Log.e(TAG, nameOfGuarantor );
        // Log.e(TAG,  idnumberOfGuarantor );
        guarantorModels.add(new MembersModel(idnumberOfGuarantor, nameOfGuarantor, "", "", ""));
        adapter.notifyDataSetChanged();

    }

    private void submit() {
        String loan_id = session.getLoanId();
        String applicant_id = session.getIdNumber();
        String name;
        String id_number;
        String amount;
        List<MembersModel> guarantorsSubmit = new ArrayList<>();


        for (int i = 0; i < SelectGuarantorAdapter.membersModels.size(); i++) {
            Log.e(TAG, "submit: " + SelectGuarantorAdapter.membersModels.size());
            name = SelectGuarantorAdapter.membersModels.get(i).name;
            id_number = SelectGuarantorAdapter.membersModels.get(i).id_number;
            amount = SelectGuarantorAdapter.membersModels.get(i).amount;
            if (amount.isEmpty()) {
                adapter.notifyItemChanged(i);
                Toast.makeText(this, "Check amount for each guarantor", Toast.LENGTH_SHORT).show();
                break;

            }

            guarantorsSubmit.add(SelectGuarantorAdapter.membersModels.get(i));


            Log.e(TAG, "submit: " + "Name --->" + name + " Id number " + id_number + " Amount " + amount + " Applicant Id " + applicant_id);
        }
        Log.e(TAG, "no of guarantors to submit"+guarantorsSubmit.size() );

//            mService.insertGuarantors(loan_id, id_number, amount, applicant_id).enqueue(new Callback<GuarantorModel>() {
//                @Override
//                public void onResponse(Call<GuarantorModel> call, Response<GuarantorModel> response) {
//                    Log.e(TAG, "onResponse: " + response);
//                    AppUtilits.dismissDialog();
//                    Intent intent = new Intent(GetGuarantorActivity.this, MainActivity.class);
//                    startActivity(intent);
//
//                }
//
//                @Override
//                public void onFailure(Call<GuarantorModel> call, Throwable t) {
//                    AppUtilits.dismissDialog();
//                    Toast.makeText(GetGuarantorActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();
//
//                }
//            });




    }

    private void insertGuarantors(String loan_id, String member_id, String amount, String applicant_id) {
        Log.e(TAG, "insertGuarantors: ");

        mService.insertGuarantors(loan_id, member_id, amount, applicant_id).enqueue(new Callback<GuarantorModel>() {
            @Override
            public void onResponse(Call<GuarantorModel> call, Response<GuarantorModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GetGuarantorActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " + response.body());
                    Log.e("Values", "" + response.body().toString());

                    Intent mainIntent = new Intent(GetGuarantorActivity.this, MainActivity.class);
                    startActivity(mainIntent);

                } else {
                    Toast.makeText(GetGuarantorActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GuarantorModel> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }


}

