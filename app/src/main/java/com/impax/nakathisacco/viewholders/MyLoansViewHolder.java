package com.impax.nakathisacco.viewholders;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.impax.nakathisacco.Model.Genre;
import com.impax.nakathisacco.Model.MyLoan;
import com.impax.nakathisacco.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.text.DecimalFormat;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MyLoansViewHolder extends GroupViewHolder {

    private TextView tvDate,tvStatus,tvAmount;
    private ImageView arrow;
    private ImageView icon;

    public MyLoansViewHolder(View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.tv_loan_date);
        tvStatus= itemView.findViewById(R.id.tv_loan_status);
        tvAmount= itemView.findViewById(R.id.tv_loan_amount);
        //tvDate = itemView.findViewById(R.id.tv_loan_date);
        arrow = itemView.findViewById(R.id.list_item_genre_arrow);
        icon = itemView.findViewById(R.id.list_item_genre_icon);
    }

    public void setGenreTitle(ExpandableGroup myloan) {
        if (myloan instanceof MyLoan) {
            double amount = Double.parseDouble(((MyLoan) myloan).p_amount);
            DecimalFormat format = new DecimalFormat("#,###");
            tvAmount.setText(format.format(amount));

            tvDate.setText(myloan.getTitle());
            //tvStatus.setText(myloan.getTitle());
            String loan_status = null;
            String status = ((MyLoan) myloan).status;
            switch(status)
            {
                case "0":
                    loan_status = "Guarantors";
                    break;
                case "1":
                    loan_status = "Committee";
                    break;
                case "2":
                    loan_status = "Cleared";
                    break;
                case "3":
                    loan_status = "Rejected";
                    break;
                case "4":
                    loan_status = "Paid";
                    break;

            }
            tvStatus.setText(loan_status);

           // icon.setBackgroundResource(((MyLoan) myloan).getIconResId());
        }
//        if (genre instanceof MultiCheckGenre) {
//            genreName.setText(genre.getTitle());
//            icon.setBackgroundResource(((MultiCheckGenre) genre).getIconResId());
//        }
//        if (genre instanceof SingleCheckGenre) {
//            genreName.setText(genre.getTitle());
//            icon.setBackgroundResource(((SingleCheckGenre) genre).getIconResId());
//        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
