package com.example.nakathisacco.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.MemberDetails;
import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.R;
import com.example.nakathisacco.Retrofit.INakathiAPI;

import java.util.List;


public class SelectGuarantorAdapter extends RecyclerView.Adapter<SelectGuarantorAdapter.MyViewHolder> {
    private Context mcontext;
    public static List<MembersModel> membersModels;
    INakathiAPI mService;
     String loan_id;




    public SelectGuarantorAdapter(Context mcontext, List<MembersModel> membersModels) {
        this.mcontext = mcontext;
        this.membersModels = membersModels;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public SelectGuarantorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View itemView = inflater.inflate(R.layout.item_guarantor_form, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final  SelectGuarantorAdapter.MyViewHolder holder, final int position) {
        final MembersModel membersModel= membersModels.get(position);

        holder.edTFname.setText(membersModel.name);
        holder.edTFname.setEnabled(false);
        holder.edtId.setText(membersModel.id_number);
        holder.edtId.setEnabled(false);
        holder.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                membersModels.get(position).setAmount(holder.edtAmount.getText().toString());
                notifyDataSetChanged();
                holder.edtAmount.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                membersModels.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                //notifyItemChanged(holder.getAdapterPosition());
               // notifyItemRangeRemoved(holder.getAdapterPosition(),getItemCount());
                Toast.makeText(mcontext, "remove clicked", Toast.LENGTH_SHORT).show();

            }
        });










    }



    @Override
    public int getItemCount() {
        return membersModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText edTFname, edtId, edtAmount;
        public ImageView imageView;
        private Button btnRemoveItem;
        public View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edTFname = itemView.findViewById(R.id.fnamesGuarantor1Edt);
            edtId = itemView.findViewById(R.id.idGuarantor1Edt);
            edtAmount = itemView.findViewById(R.id.edt_amount);
            btnRemoveItem = itemView.findViewById(R.id.btn_delete);

            mView = itemView;


        }

    }

}
