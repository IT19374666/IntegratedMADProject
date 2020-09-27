package com.mad_mini_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class AccountAdapter extends ArrayAdapter<Account> {
    Context context;
    //String[] titles;
    ArrayList<String> titles;
    List<Account> accountList;
    int[] images ;
    String[] contents;
    // accArraySpinner = new String[]{ "Choose Account Type....", "Savings", "Credit card","Cash" };

    AccountAdapter(Context context, ArrayList<Account> accountList){
        super(context,R.layout.single_raw_accounts, accountList);
        this.context = context;
        //this.images = images;
        //this.titles = titles;
        //this.contents = contents;
        this.accountList = accountList;
        images = new int[]{R.drawable.creaditwallet, R.drawable.moneywallet, R.drawable.savingwallet, R.drawable.otherwallet};

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = li.inflate(R.layout.single_raw_accounts,parent,false);
        TextView title = row.findViewById(R.id.title);
        TextView content = row.findViewById(R.id.content);
        TextView type = row.findViewById(R.id.text_acc_type);

        Account account = accountList.get(position);
        //imageView.setImageResource(images[position]);
        title.setText(account.getAccName());
        type.setText(account.getAccType());
        content.setText(String.valueOf(account.getBalance()));
        /*if(account.getAccType() == "Credit card"){
            imageView.setImageResource(images[0]);
        }
        else if(account.getAccType() == "Cash"){
            imageView.setImageResource(images[1]);
        }

        else if(account.getAccType() == "Savings"){
            imageView.setImageResource(images[2]);
        }
        else {
            imageView.setImageResource(images[2]);
        }*/



        return row;
    }
}