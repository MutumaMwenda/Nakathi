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
import android.widget.Toast;

import com.impax.nakathisacco.ContributionDetails;
import com.impax.nakathisacco.Model.Contribution;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.Retrofit.INakathiAPI;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ContributionDetailsAdapter extends RecyclerView.Adapter<ContributionDetailsAdapter.MyViewHolder> {
    private Context mcontext;
    private List<Contribution> mContributions;
    INakathiAPI mService;
     String loan_id;




    public ContributionDetailsAdapter(Context mcontext, List<Contribution> contributions) {
        this.mcontext = mcontext;
        this.mContributions= contributions;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public ContributionDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_contributions_details, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContributionDetailsAdapter.MyViewHolder holder, final int position) {
        final Contribution contribution= mContributions.get(position);


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp da = java.sql.Timestamp.valueOf(contribution.contribution_date ) ;
       // java.sql.Timestamp dt = java.sql.Timestamp.valueOf(driver.assigned_up_to_date ) ;
        Date date_da= new Date(da.getTime());
       // Date date_to= new Date(dt.getTime());
        String c_date = formatter.format(date_da);
       // String assigned_up_to_date = formatter.format(date_to);


        holder.tvTrans.setText(contribution.name);
        DecimalFormat formatter2 = new DecimalFormat("#,###");
        double amount = Double.parseDouble(contribution.amount);
        holder.tvAmount.setText("Amount : "+formatter2.format(amount));
        holder.tvDate.setText(c_date);
       // DecimalFormat formatter2 = new DecimalFormat("#,###");
        //holder.tvSname.setText(driver.s_name);
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mcontext, "Working HERE", Toast.LENGTH_SHORT).show();
//
//                Intent  intent = new Intent(mcontext, ContributionDetails.class);
//                intent.putExtra(ContributionDetails.CONTRIBUTION_DETAILS_ITEM_KEY, mContributions.get(position));
//                mcontext.startActivity(intent);
//            }
//        });









    }

    @Override
    public int getItemCount() {
        return mContributions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTrans, tvDate, tvAmount;
        public ImageView imageView;
        private Button btnApprove;
        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrans = itemView.findViewById(R.id.trans_id);
            tvAmount= itemView.findViewById(R.id.c_amount);
            tvDate= itemView.findViewById(R.id.c_date);
            //tvAssigned_to = itemView.findViewById(R.id.tv_date_assigned_up_to);
            //tvBorrowedOn = itemView.findViewById(R.id.tv_borrowed_on);
            //imageView = itemView.findViewById(R.id.list_imageview);
            mView = itemView;
        }


    }

}
