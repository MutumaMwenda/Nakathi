package com.example.nakathisacco.adapters;

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

import com.example.nakathisacco.MemberDetails;
import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.R;
import com.example.nakathisacco.Retrofit.INakathiAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {
    private Context mcontext;
    private List<LoanApplicantModel> LoanApplicantModel;
    INakathiAPI mService;
     String loan_id;




    public NotificationsAdapter(Context mcontext, List<LoanApplicantModel> LoanApplicantModel) {
        this.mcontext = mcontext;
        this.LoanApplicantModel = LoanApplicantModel;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public NotificationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_notifications, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.MyViewHolder holder, final int position) {
        final LoanApplicantModel loanApplicantModel= LoanApplicantModel.get(position);


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(loanApplicantModel.date_requested ) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);


        holder.tvName.setText("Name: "+loanApplicantModel.name);
        holder.tvBorrowedOn.setText("Borrowed on: "+strDate);
        holder.tvLoan.setText("Request Amount: "+loanApplicantModel.amount_requested);
         loan_id = loanApplicantModel.loan_id;

    }

    @Override
    public int getItemCount() {
        return LoanApplicantModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDate, tvLoan, tvBorrowedOn;
        public ImageView imageView;
        private Button btnApprove;
        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.firstname);
            tvLoan = itemView.findViewById(R.id.loan);
            tvDate = itemView.findViewById(R.id.date);
            tvBorrowedOn = itemView.findViewById(R.id.tv_borrowed_on);
            imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }


    }

}
