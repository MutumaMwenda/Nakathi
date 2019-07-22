package com.impax.nakathisacco.adapters;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.R;

import java.util.ArrayList;


public class GuarantorCursorAdapter extends CursorAdapter {


    public static ArrayList<MembersModel> membersModels = new ArrayList<>();
    public LayoutInflater mInflater;


    public GuarantorCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.item_guarantor_cursor, parent, false);
        return v;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}