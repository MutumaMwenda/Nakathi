package com.impax.nakathisacco;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.impax.nakathisacco.Model.Vehicle;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.fragments.ContributionFragment;
import com.impax.nakathisacco.fragments.DriversFragment;
import com.impax.nakathisacco.fragments.LicenceFragment;

public class AssetDetails extends AppCompatActivity {
    private Button btnApprove,btnReject;
    private String TAG = AssetDetails.class.getSimpleName();
    public static final String ASSET_ITEM_KEY = "asset_item_key";
    TextView tvTitle,tvRequestedAmount,tvBorrowedOn,tvBorrowedAmount,tvNoOfGuarantors;
    INakathiAPI mService;
    Vehicle vehicle;
    private Session session;
    String status,member_id;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    String TabNames[]={"Licence","Staff","Contributions"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTitle = findViewById(R.id.tv_title);


        viewPager = findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        vehicle = getIntent().getExtras().getParcelable(AssetDetails.ASSET_ITEM_KEY);
        if (vehicle== null) {
            throw new AssertionError("No data item received!");
        } else {
           // Toast.makeText(this, "itmem: " + vehicle, Toast.LENGTH_SHORT).show();

        }
        tvTitle.setText(vehicle.reg_no);



    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                     return LicenceFragment.newInstance("new",vehicle);
                case 1:
                    return DriversFragment.newInstance("approved",vehicle);

                case 2:
                    return  ContributionFragment.newInstance("rejected",vehicle);

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return TabNames[0];
                case 1:
                    return TabNames[1];
                case 2:
                    return TabNames[2];

            }
            return "";
        }
    }

}
