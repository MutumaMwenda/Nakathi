package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.Driver;
import com.impax.nakathisacco.Model.License;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.Retrofit.INakathiAPI;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class LicenceAdapter extends RecyclerView.Adapter<LicenceAdapter.MyViewHolder> {
    private Context mcontext;
    private List<License> mLicenses;
    INakathiAPI mService;
     String loan_id;




    public LicenceAdapter(Context mcontext, List<License> licenses) {
        this.mcontext = mcontext;
        this.mLicenses = licenses;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public LicenceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_cert, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LicenceAdapter.MyViewHolder holder, final int position) {
        final License license= mLicenses.get(position);


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp da = java.sql.Timestamp.valueOf(license.activefrom) ;
        java.sql.Timestamp dt = java.sql.Timestamp.valueOf(license.expiryDate ) ;
        Date date_da= new Date(da.getTime());
        Date date_to= new Date(dt.getTime());
        String date_active = formatter.format(date_da);
        String date_expire = formatter.format(date_to);


        holder.tvName.setText(license.name);
        holder.tvactive.setText("Active From: "+date_active);
        holder.tvExp.setText("Expiry Date: "+date_expire);
        DecimalFormat formatter2 = new DecimalFormat("#,###");
        int checkStatus = Integer.parseInt(license.status);
        String status;
        switch(checkStatus)
        {
            case 1:
                status = "New";
                holder.tvStatusColor.setBackgroundColor(Color.GREEN);
                holder.tvStatusColor.setText(status);

                break;

            case 0:
                status = "Warning";
                holder.tvStatusColor.setBackgroundColor(Color.parseColor("#FFA500"));
                holder.tvStatusColor.setText(status);


                break;

            case 2:
                status ="Expired";
                holder.tvStatusColor.setBackgroundColor(Color.RED);
                holder.tvStatusColor.setText(status);

                break;

        }
      //  holder.tvStatus.setText(license.status);









    }

    @Override
    public int getItemCount() {
        return mLicenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvactive, tvExp, tvStatus,tvStatusColor;
        public ImageView imageView;
        private Button btnApprove;
        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvl_name);
            tvactive= itemView.findViewById(R.id.tv_active_from);
            tvExp= itemView.findViewById(R.id.tv_expiryDate);
            tvStatus= itemView.findViewById(R.id.tv_status);
            tvStatusColor= itemView.findViewById(R.id.tv_statusColor);
            //tvBorrowedOn = itemView.findViewById(R.id.tv_borrowed_on);
            imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }


    }

}
