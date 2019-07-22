package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.R;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SavingsLogAdapter extends RecyclerView.Adapter<SavingsLogAdapter.MyViewHolder> {

    private Context mcontext;
    private List<SavingsLogModel> savingsLogModels;
    // INakathiAPI mService;
    //  String status;




    public SavingsLogAdapter(Context mcontext, List<SavingsLogModel> savingsLogModels) {
        this.mcontext = mcontext;
        this.savingsLogModels= savingsLogModels;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_savings, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final SavingsLogModel savingsLogModel=savingsLogModels.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        Timestamp ts = Timestamp.valueOf( savingsLogModel.contribution_date ) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);
        holder.tvRegno.setText(savingsLogModel.contributor);
        holder.tvAmount.setText(savingsLogModel.amount);
        holder.tvDepositedDate.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return savingsLogModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRegno,tvAmount, tvDepositedDate;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRegno = itemView.findViewById(R.id.tv_reg_no);
            //tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvDepositedDate = itemView.findViewById(R.id.tv_deposited_date);
            // imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }

    }
}
