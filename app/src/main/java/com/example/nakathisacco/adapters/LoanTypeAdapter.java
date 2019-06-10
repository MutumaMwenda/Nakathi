package com.example.nakathisacco.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nakathisacco.Model.LoanTypeModel;
import com.example.nakathisacco.R;

import java.util.ArrayList;



public class LoanTypeAdapter extends BaseAdapter {

    public static ArrayList<LoanTypeModel> loanTypeModels = new ArrayList<>();
    public LayoutInflater mInflater;

    public LoanTypeAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemRowHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.loan_type_menu, parent, false);
            vh = new ItemRowHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ItemRowHolder) convertView.getTag();
        }
        showData(vh.label, position);
        return convertView;
    }

    public LoanTypeModel getItem(int position) {

        return loanTypeModels.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {

        return loanTypeModels.size();
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
        LoanTypeModel p = loanTypeModels.get(position);
        textView.setText(p.name);
    }

    private class ItemRowHolder {
        TextView label;

        ItemRowHolder(View row) {
            label = row.findViewById(R.id.txtDropDownLabel);
        }
    }

    private class ItemRow {
        TextView label;

        ItemRow(View row) {
            label = row.findViewById(R.id.txtDropDownLabel);
        }
    }
}