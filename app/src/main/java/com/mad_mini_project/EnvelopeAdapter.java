package com.mad_mini_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EnvelopeAdapter extends ArrayAdapter<Envelope> {

    Context context;
    ArrayList<Envelope> envelopeList;

    EnvelopeAdapter(Context context, ArrayList<Envelope> envelopeList){
        super(context,R.layout.single_raw_envelope, envelopeList);
        this.context = context;
        this.envelopeList = envelopeList;

    }

    @NonNull

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = li.inflate(R.layout.single_raw_envelope, parent, false);
        TextView name = row.findViewById(R.id.titleName);
        TextView amount = row.findViewById(R.id.titleAmount);
        //TextView month = row.findViewById(R.id.titleMonth);

        Envelope envelope = envelopeList.get(position);

        name.setText(envelope.getName());
        amount.setText(String.valueOf(envelope.getAmount()));
        //month.setText(envelope.getMonth());


        return row;
    }

}
