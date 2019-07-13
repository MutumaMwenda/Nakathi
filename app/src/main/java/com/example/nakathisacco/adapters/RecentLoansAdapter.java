package com.example.nakathisacco.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nakathisacco.ApproveLoanActivity;
import com.example.nakathisacco.AssetsActivity;
import com.example.nakathisacco.CertsActivity;
import com.example.nakathisacco.LoanBalanceActivity;
import com.example.nakathisacco.Model.Loan;
import com.example.nakathisacco.MyLoansActivity;
import com.example.nakathisacco.R;
import com.example.nakathisacco.SavingsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecentLoansAdapter extends RecyclerView.Adapter<RecentLoansAdapter.MyViewHolder> {

    private Context mcontext;
    private List<Loan> loans;




    public RecentLoansAdapter(Context mcontext, List<Loan> loans) {
        this.mcontext = mcontext;
        this.loans= loans;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_myloan, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Loan loan=loans.get(position);
        String loan_status = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(loan.date_requested) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);
        String status = loan.status;
        switch("0")
        {
            case "0":
                loan_status = "Draft";
                break;
            case "1":
                loan_status = "Approved";
                break;
            case "2":
                loan_status = "Rejected";
                break;
            case "3":
                loan_status = "Self";
                break;
            case "4":
                loan_status = "Draft";
                break;

        }
        holder.tvStatus.setText(loan_status);
        holder.tvAmount.setText(loan.p_amount);
        holder.tvDate.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStatus,tvAmount, tvView ,tvAction,tvDate;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_loanDate);
            tvAmount = itemView.findViewById(R.id.tv_loanAmount);
            tvStatus = itemView.findViewById(R.id.tv_loanStatus);
           // tvView = itemView.findViewById(R.id.tv_loanView);
            tvAction = itemView.findViewById(R.id.tv_loanAction);
            mView = itemView;
        }

    }

}
