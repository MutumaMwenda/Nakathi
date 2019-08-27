package com.impax.nakathisacco.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.impax.nakathisacco.Model.Artist;
import com.impax.nakathisacco.Model.Genre;
import com.impax.nakathisacco.Model.GuarantorModel;
import com.impax.nakathisacco.Model.MyLoan;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.viewholders.ArtistViewHolder;
import com.impax.nakathisacco.viewholders.GenreViewHolder;
import com.impax.nakathisacco.viewholders.LoanDetailsViewHolder;
import com.impax.nakathisacco.viewholders.MyLoansViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;
public class MyLoansAdapter extends ExpandableRecyclerViewAdapter<MyLoansViewHolder, LoanDetailsViewHolder> {

    public MyLoansAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public MyLoansViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_myloans, parent, false);
        return new MyLoansViewHolder(view);
    }

    @Override
    public LoanDetailsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_myloans_details, parent, false);
        return new LoanDetailsViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(LoanDetailsViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final GuarantorModel guarantorModel = ((MyLoan) group).getItems().get(childIndex);
        holder.setArtistName(guarantorModel.name,guarantorModel.amount,guarantorModel.status);
    }

    @Override
    public void onBindGroupViewHolder(MyLoansViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setGenreTitle(group);
    }


}

