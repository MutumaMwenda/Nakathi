package com.impax.nakathisacco.viewholders;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.Genre;
import com.impax.nakathisacco.Model.MyLoan;
import com.impax.nakathisacco.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class LoanDetailsViewHolder extends ChildViewHolder {

    private TextView childTextView;
    private TextView tvAmount;
    private TextView tvStatus;

    public LoanDetailsViewHolder(View itemView) {
        super(itemView);
        childTextView =  itemView.findViewById(R.id.tv_guarantor_name);
        tvAmount =  itemView.findViewById(R.id.tv_guarantor_amount);
        tvStatus =  itemView.findViewById(R.id.tv_guarantor_status);
    }

    public void setArtistName(String name,String amount,String status) {
        childTextView.setText(name);
        String loan_status = null;
        switch(status)
        {
            case "0":
                loan_status = "Pending";
                break;
            case "1":
                loan_status = "Approved";
                break;
            case "2":
                loan_status = "Rejected";
                break;


        }
        tvStatus.setText(loan_status);
        tvAmount.setText(amount);
    }
}