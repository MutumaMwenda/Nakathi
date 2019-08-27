package com.impax.nakathisacco.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.GuarantorModel;
import com.impax.nakathisacco.Model.Loan;
import com.impax.nakathisacco.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailsLoansAdapter extends RecyclerView.Adapter<DetailsLoansAdapter.MyViewHolder> {

    private Context mcontext;
    private List<GuarantorModel> guarantorModels;




    public DetailsLoansAdapter(Context mcontext, List<GuarantorModel> guarantorModels) {
        this.mcontext = mcontext;
        this.guarantorModels= guarantorModels;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_guarantor_details, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final GuarantorModel guarantorModel=guarantorModels.get(position);
        String loan_status = null;


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        //java.sql.Timestamp ts = java.sql.Timestamp.valueOf(loan.date_requested) ;
        //Date date = new Date(ts.getTime());
        //String strDate = formatter.format(date);
        String status = guarantorModel.status;
        switch(status)
        {
            case "0":
                loan_status = "Pending";
                break;
            case "1":
                loan_status = "Approved";
                break;


        }
        holder.tvStatus.setText(loan_status);
        double amount = Double.parseDouble(guarantorModel.amount);
        DecimalFormat format = new DecimalFormat("#,###");
        holder.tvAmount.setText(format.format(amount));
        //holder.tvDate.setText(strDate);
        holder.tvName.setText(guarantorModel.name);

    }

    @Override
    public int getItemCount() {
        return guarantorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvStatus, tvDate,tvAmount;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvDate = itemView.findViewById(R.id.tv_loanDate);
            tvAmount = itemView.findViewById(R.id.tv_g_amount);
            tvStatus = itemView.findViewById(R.id.tv_g_status);
           // tvView = itemView.findViewById(R.id.tv_loanView);
            tvName= itemView.findViewById(R.id.tv_g_name);
            mView = itemView;
        }

    }

}
