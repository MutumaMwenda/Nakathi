package com.impax.nakathisacco.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.impax.nakathisacco.Model.LoanTypeModel;
import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.R;

import java.util.ArrayList;



public class GuarantorAdapter extends BaseAdapter {

    public static ArrayList<MembersModel> membersModels = new ArrayList<>();
    public LayoutInflater mInflater;

    public GuarantorAdapter(Context context) {

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

    public MembersModel getItem(int position) {

        return membersModels.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {

        return membersModels.size();
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
        MembersModel p = membersModels.get(position);
        textView.setText(p.getName());
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