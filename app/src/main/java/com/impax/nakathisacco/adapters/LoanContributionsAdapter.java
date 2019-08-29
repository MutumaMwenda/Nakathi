package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.ContributionActivity;
import com.impax.nakathisacco.Model.ContributionTypes;
import com.impax.nakathisacco.Model.ContributionsModel;
import com.impax.nakathisacco.Model.Loan;
import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoanContributionsAdapter extends RecyclerView.Adapter<LoanContributionsAdapter.MyViewHolder> {

    private Context mcontext;
   // private List<ContributionTypes> mContributionTypes;
    private  String amount;
     private List<Loan> mLoans;
    // INakathiAPI mService;
    //  String status;




    public LoanContributionsAdapter(Context mcontext, List<Loan> loans) {
        this.mcontext = mcontext;
       // this.mContributionTypes= contributionTypes;
        this.mLoans=loans;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public LoanContributionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_loan_contribution, parent, false);
        LoanContributionsAdapter.MyViewHolder viewHolder = new LoanContributionsAdapter.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoanContributionsAdapter.MyViewHolder holder, final int position) {

        final Loan loan=mLoans.get(position);

//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
//        Timestamp ts = Timestamp.valueOf( contributionsModel.date_requested ) ;
//        Date date = new Date(ts.getTime());
//        String strDate = formatter.format(date);

        holder.tvLoanType.setText(loan.type);
        holder.tvLoanBalance.setText(loan.loanbalance);
        holder.tvAmount.setText(loan.amount);

        //amount=holder.edx_contribution_amount.getText();

        holder.tvAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loan.amount= charSequence.toString();

                //  notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return mLoans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLoanType,tvLoanBalance;
        public EditText tvAmount;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLoanType = itemView.findViewById(R.id.tv_loan_type);
            //tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
            tvLoanBalance = itemView.findViewById(R.id.tv_loan_balance);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            // tvDepositedDate = itemView.findViewById(R.id.tv_deposited_date);
            // imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }

    }
}
