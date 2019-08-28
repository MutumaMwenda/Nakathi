package com.impax.nakathisacco.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.impax.nakathisacco.Model.LoanTypeModel;
import com.impax.nakathisacco.Model.Vehicle;
import com.impax.nakathisacco.R;

import java.util.ArrayList;



public class SuggestAdapter extends BaseAdapter implements Filterable {

    public static ArrayList<Vehicle> mVehicles = new ArrayList<>();
    public LayoutInflater mInflater;

    public SuggestAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemRowHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.vehicle_menu, parent, false);
            vh = new ItemRowHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ItemRowHolder) convertView.getTag();
        }
        showData(vh.label, position);
        return convertView;
    }

    public Vehicle getItem(int position) {

        return mVehicles.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {

        return mVehicles.size();
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ItemRow vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.loan_type_row, parent, false);
            vh = new ItemRow(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ItemRow) convertView.getTag();
        }
        showData(vh.label, position);
        return convertView;
    }

    private void showData(TextView textView, int position) {
        Vehicle p = mVehicles.get(position);
        textView.setText(p.reg_no);
    }

    private class ItemRowHolder {
        TextView label;

        ItemRowHolder(View row) {
            label = row.findViewById(R.id.tv_dropdown_vehicle);
        }
    }

    private class ItemRow {
        TextView label;

        ItemRow(View row) {
            label = row.findViewById(R.id.tv_dropdown_vehicle);
        }
    }
    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mVehicles;
                    filterResults.count = mVehicles.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }
}