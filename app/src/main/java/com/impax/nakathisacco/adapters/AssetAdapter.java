package com.impax.nakathisacco.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.AssetDetails;
import com.impax.nakathisacco.AssetRevenueActivity;
import com.impax.nakathisacco.GuarantorActivity;
import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Model.Vehicle;
import com.impax.nakathisacco.R;
import com.impax.nakathisacco.Retrofit.INakathiAPI;

import java.util.List;


public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.MyViewHolder> {
    private Context mcontext;
    private AssetsModel assetsModel;
    String regNo,routeName;
   /*
   INakathiAPI mService;
   String status;
   */





    public AssetAdapter(Context mcontext, AssetsModel assetsModel) {
        this.mcontext = mcontext;
        this.assetsModel= assetsModel;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_asset, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Vehicle vModel= assetsModel.message.get(position);

        holder.tvVehicle.setText(vModel.reg_no.toUpperCase());
       // holder.tvRoute.setText("Route : "+assetsModel.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mcontext, "You clicked", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(mcontext, AssetDetails.class);
                i.putExtra(AssetDetails.ASSET_ITEM_KEY, assetsModel.message.get(position));

                String strReg = vModel.reg_no;
                i.putExtra("reg_no", strReg);
             //   String strName = assetsModel.name;
               /// i.putExtra("route_name", strName);
                mcontext.startActivity(i);
               // mcontext.startActivity(new Intent(mcontext, AssetRevenueActivity.class));


            }
        });

    }

    @Override
    public int getItemCount() {
        return assetsModel.message.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvVehicle, tvRoute;
        public ImageView imageView;

        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicle = itemView.findViewById(R.id.tv_vehicle);
            //tvExpiryDate = itemView.findViewById(R.id.tv_expiryDate);
            tvRoute = itemView.findViewById(R.id.tv_route);
           // tvStatusColor = itemView.findViewById(R.id.tv_statusColor);
           // imageView = itemView.findViewById(R.id.list_imageview);
           // itemView.setOnClickListener(this);
            mView = itemView;
        }

        @Override
        public void onClick(View v) {



        }
    }

}
