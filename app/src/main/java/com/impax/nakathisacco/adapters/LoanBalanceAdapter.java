package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.AssetsRevenueModel;
import com.impax.nakathisacco.Model.LoanBalanceModel;
import com.impax.nakathisacco.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoanBalanceAdapter extends RecyclerView.Adapter<LoanBalanceAdapter.MyViewHolder> {

    private Context mcontext;
    private List<LoanBalanceModel> loanBalanceModels;
    // INakathiAPI mService;
    //  String status;




    public LoanBalanceAdapter(Context mcontext, List<LoanBalanceModel> loanBalanceModels) {
        this.mcontext = mcontext;
        this.loanBalanceModels= loanBalanceModels;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_loan_balance, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final LoanBalanceModel loanBalanceModel=loanBalanceModels.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(loanBalanceModel.date_paid ) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);
        holder.tvReceivedBy.setText(loanBalanceModel.received_by);
        holder.tvAmount.setText(loanBalanceModel.amount);
        holder.tvPaidDate.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return loanBalanceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvReceivedBy,tvAmount, tvPaidDate;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceivedBy = itemView.findViewById(R.id.tv_received_by);
            //tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvPaidDate = itemView.findViewById(R.id.tv_date_paid);
            // imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }

    }

}
