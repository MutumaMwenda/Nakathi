package com.example.nakathisacco.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nakathisacco.Model.CertsModel;
import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.R;
import com.example.nakathisacco.Retrofit.INakathiAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CertsAdapter extends RecyclerView.Adapter<CertsAdapter.MyViewHolder> {
    private Context mcontext;
    private List<CertsModel> certsModels;
    INakathiAPI mService;
     String status;




    public CertsAdapter(Context mcontext, List<CertsModel> certsModel) {
        this.mcontext = mcontext;
        this.certsModels= certsModel;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public CertsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_cert, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CertsAdapter.MyViewHolder holder, final int position) {

        final CertsModel certsModel=certsModels.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(certsModel.expiryDate ) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);



        holder.tvLicense.setText(certsModel.name);
        holder.tvExpiryDate.setText("Expiry Date : "+strDate);
        int checkStatus = Integer.parseInt(certsModel.status);
        switch(checkStatus)
        {
            case 0:
                status = "New";
                holder.tvStatusColor.setBackgroundColor(Color.GREEN);
                holder.tvStatusColor.setText(status);

                break;

            case 1:
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

        holder.tvStatus.setText("Status : "+status);








    }

    @Override
    public int getItemCount() {
        return certsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLicense, tvExpiryDate, tvStatus, tvStatusColor;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLicense = itemView.findViewById(R.id.tv_license);
            tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvStatusColor = itemView.findViewById(R.id.tv_statusColor);
            imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }


    }

}
