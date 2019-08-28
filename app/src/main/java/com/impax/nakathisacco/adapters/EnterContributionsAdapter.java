package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.ContributionTypes;
import com.impax.nakathisacco.Model.ContributionsModel;
import com.impax.nakathisacco.Model.Loan;
import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EnterContributionsAdapter extends RecyclerView.Adapter<EnterContributionsAdapter.MyViewHolder> {

    private Context mcontext;
    private List<ContributionTypes> mContributionTypes;
    private  String amount;
    // private List<Loan> mLoans;
    // INakathiAPI mService;
    //  String status;




    public EnterContributionsAdapter(Context mcontext, List<ContributionTypes> contributionTypes) {
        this.mcontext = mcontext;
        this.mContributionTypes= contributionTypes;
        //this.mLoans=loans;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public EnterContributionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_contribution, parent, false);
        EnterContributionsAdapter.MyViewHolder viewHolder = new EnterContributionsAdapter.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EnterContributionsAdapter.MyViewHolder holder, final int position) {

        final ContributionTypes contributionType=mContributionTypes.get(position);

//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
//        Timestamp ts = Timestamp.valueOf( contributionsModel.date_requested ) ;
//        Date date = new Date(ts.getTime());
//        String strDate = formatter.format(date);

        holder.tvContribution.setText(contributionType.name);
        holder.tvAmount.setText(contributionType.amount);
        amount=holder.tvAmount.getText().toString();




    }

    @Override
    public int getItemCount() {
        return mContributionTypes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContribution;
        public EditText tvAmount;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContribution = itemView.findViewById(R.id.tv_contribution);
            //tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            // tvDepositedDate = itemView.findViewById(R.id.tv_deposited_date);
            // imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }

    }
}
