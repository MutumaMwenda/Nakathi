package com.example.nakathisacco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nakathisacco.MainActivity;
import com.example.nakathisacco.R;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder> {
    private final String[] web;
    private final int[] Imageid;
    private Context mContext;
    private int padding;
    private boolean home;

    public HomeAdapter(String[] web, int[] imageid, Context mContext) {
        this.web = web;
        Imageid = imageid;
        this.mContext = mContext;
        if (mContext instanceof MainActivity) {
            home = true;
        }
    }

    @NonNull
    @Override
    public HomeAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new MyHolder(inflater.inflate(R.layout.row_grid, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyHolder holder, final int position) {
        if (position != 0) {

            if (position == 1) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 2) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 3) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 4) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 5) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 6) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 7) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            } else if (position == 9) {
                holder.frameLayout.setBackgroundResource(R.drawable.circle_pink);
            }
            //frameLayout.setPadding(padding, padding, padding, padding);
            //frameLayout.setCropToPadding(true);
        }
        holder.textView.setText(web[position]);
        holder.imageView.setImageResource(Imageid[position]);
    }

    @Override
    public int getItemCount() {
        return web.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public FrameLayout frameLayout;
        public ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            frameLayout = itemView.findViewById(R.id.fl_image);
            imageView = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Home", "onClick: adapter position is " + getAdapterPosition());
                    ((MainActivity) mContext).itemClicked(getAdapterPosition(), v);

                }
            });
        }
    }
}
