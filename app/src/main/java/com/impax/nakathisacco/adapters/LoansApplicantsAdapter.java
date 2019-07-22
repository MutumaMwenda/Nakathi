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
import com.impax.nakathisacco.Model.LoanApplicantModel;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.Retrofit.INakathiAPI;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class LoansApplicantsAdapter extends RecyclerView.Adapter<LoansApplicantsAdapter.MyViewHolder> {
    private Context mcontext;
    private List<LoanApplicantModel> LoanApplicantModel;
    INakathiAPI mService;
     String loan_id;




    public LoansApplicantsAdapter(Context mcontext, List<LoanApplicantModel> LoanApplicantModel) {
        this.mcontext = mcontext;
        this.LoanApplicantModel = LoanApplicantModel;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public LoansApplicantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_loan_applicant, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoansApplicantsAdapter.MyViewHolder holder, final int position) {
        final LoanApplicantModel loanApplicantModel= LoanApplicantModel.get(position);


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(loanApplicantModel.date_requested ) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);


        holder.tvName.setText("Name: "+loanApplicantModel.name);
        holder.tvBorrowedOn.setText("Borrowed On: "+strDate);
        double amount = Double.parseDouble(loanApplicantModel.amount_requested);
        DecimalFormat formatter2 = new DecimalFormat("#,###");
        holder.tvLoan.setText("Amount in Kshs: "+formatter2.format(amount));
         loan_id = loanApplicantModel.loan_id;

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(mcontext, MemberDetails.class);
                        intent.putExtra(MemberDetails.MEMBER_ITEM_KEY, LoanApplicantModel.get(position));
                        mcontext.startActivity(intent);


            }
        });






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
            btnApprove = itemView.findViewById(R.id.btn_approve);
            tvBorrowedOn = itemView.findViewById(R.id.tv_borrowed_on);
            imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }


    }

}
