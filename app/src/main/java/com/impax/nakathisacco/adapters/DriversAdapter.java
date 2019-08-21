package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.MemberDetails;
import com.impax.nakathisacco.Model.Driver;
import com.impax.nakathisacco.Model.LoanApplicantModel;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.Retrofit.INakathiAPI;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.MyViewHolder> {
    private Context mcontext;
    private List<Driver> mDrivers;
    INakathiAPI mService;
     String loan_id;




    public DriversAdapter(Context mcontext, List<Driver> drivers) {
        this.mcontext = mcontext;
        this.mDrivers = drivers;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public DriversAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_drivers, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriversAdapter.MyViewHolder holder, final int position) {
        final Driver driver= mDrivers.get(position);


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp da = java.sql.Timestamp.valueOf(driver.date_assigned ) ;
        java.sql.Timestamp dt = java.sql.Timestamp.valueOf(driver.assigned_up_to_date ) ;
        Date date_da= new Date(da.getTime());
        Date date_to= new Date(dt.getTime());
        String date_assigned = formatter.format(date_da);
        String assigned_up_to_date = formatter.format(date_to);


        holder.tvName.setText(driver.name);
        holder.tvDate_assigned.setText("Assigned On: "+date_assigned);
        holder.tvAssigned_to.setText("Assigned To: "+assigned_up_to_date);
        DecimalFormat formatter2 = new DecimalFormat("#,###");
        holder.tvSname.setText(driver.s_name);









    }

    @Override
    public int getItemCount() {
        return mDrivers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvSname, tvDate_assigned, tvAssigned_to;
        public ImageView imageView;
        private Button btnApprove;
        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.staff_name);
            tvSname= itemView.findViewById(R.id.s_name);
            tvDate_assigned = itemView.findViewById(R.id.tv_date_assigned);
            tvAssigned_to = itemView.findViewById(R.id.tv_date_assigned_up_to);
            //tvBorrowedOn = itemView.findViewById(R.id.tv_borrowed_on);
            imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }


    }

}
