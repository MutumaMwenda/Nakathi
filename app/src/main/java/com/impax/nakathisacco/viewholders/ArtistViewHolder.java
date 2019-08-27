package com.impax.nakathisacco.viewholders;

import android.view.View;
import android.widget.TextView;

import com.impax.nakathisacco.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ArtistViewHolder extends ChildViewHolder {

    private TextView childTextView;

    public ArtistViewHolder(View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    }

    public void setArtistName(String name) {
        childTextView.setText(name);
    }
}
