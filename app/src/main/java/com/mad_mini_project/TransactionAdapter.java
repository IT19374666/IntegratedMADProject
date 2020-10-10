package com.mad_mini_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transactions> {

    Context context;
    ArrayList<Transactions> transactionList;
    Transactions transactions;

    TransactionAdapter(Context context, ArrayList<Transactions> transactionList){
        super(context, R.layout.single_raw_transaction, transactionList);
        this.context = context;
        this.transactionList = transactionList;

    }

    @NonNull

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = li.inflate(R.layout.single_raw_transaction, parent, false);
        TextView name = row.findViewById(R.id.txtName);
        TextView description = row.findViewById(R.id.txtDescription);
        TextView amount = row.findViewById(R.id.txtAmount);
        TextView date = row.findViewById(R.id.txtDate);

        Transactions transactions = transactionList.get(position);

        name.setText(transactions.getName());
        description.setText(transactions.getDescription());
        date.setText(transactions.getDate());
        amount.setText(String.valueOf(transactions.getAmount()));



        return row;
    }

}
