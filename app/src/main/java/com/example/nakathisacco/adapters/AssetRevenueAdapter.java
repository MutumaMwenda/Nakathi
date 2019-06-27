package com.example.nakathisacco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nakathisacco.Model.AssetsRevenueModel;
import com.example.nakathisacco.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

    public class AssetRevenueAdapter extends RecyclerView.Adapter<AssetRevenueAdapter.MyViewHolder> {

        private Context mcontext;
        private List<AssetsRevenueModel> assetsRevenueModels;

        public AssetRevenueAdapter(Context mcontext, List<AssetsRevenueModel> assetsRevenueModels) {
            this.mcontext = mcontext;
            this.assetsRevenueModels= assetsRevenueModels;

        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_asset_revenue, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            final AssetsRevenueModel assetsRevenueModel=assetsRevenueModels.get(position);

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            java.sql.Timestamp ts = java.sql.Timestamp.valueOf(assetsRevenueModel.date_collected ) ;
            Date date = new Date(ts.getTime());
            String strDate = formatter.format(date);
            holder.tvRevenueType.setText(assetsRevenueModel.revenue_type);
            holder.tvAmount.setText(assetsRevenueModel.amount);
            holder.tvCollectionDate.setText(strDate);
        }

        @Override
        public int getItemCount() {
            return assetsRevenueModels.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvRevenueType,tvAmount, tvCollectionDate;
            public ImageView imageView;

            public View mView;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvRevenueType = itemView.findViewById(R.id.tv_revenue_type);
                //tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
                tvAmount = itemView.findViewById(R.id.tv_amount);
                tvCollectionDate = itemView.findViewById(R.id.tv_date_collected);
                // imageView = itemView.findViewById(R.id.list_imageview);
                mView = itemView;
            }

        }
    }


