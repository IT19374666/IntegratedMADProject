package com.mad_mini_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEnvelope extends AppCompatActivity implements AdapterView.OnClickListener {

    //declare the variables
    EditText AddEnvelopeNameText, AddBudgetAmountText;
    Button CancelButton, AddButton;
    DatabaseReference dbRef, dbRefEnvName;
    Envelope envelope;
    Spinner monthSpinner;
    String[] monthArraySpinner;
    String textMonthType = null;
    Integer itemPosition;

    //method to clear all user inputs
    private void clearControls() {
        AddEnvelopeNameText.setText("");
        AddBudgetAmountText.setText("");
        //AddMonthPeriodText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_envelope);

        CancelButton = findViewById(R.id.CancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyEnvelope.class);
                startActivity(i);
            }
        });

        AddEnvelopeNameText = findViewById(R.id.AddEnvelopeNameText);
        AddBudgetAmountText = findViewById(R.id.AddBudgetAmountText);
        //AddMonthPeriodText = findViewById(R.id.AddMonthPeriodText);


        //Implementing the spinner
//        monthArraySpinner = new String[]{"Select Month....", "Monthly", "Yearly", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthArraySpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        monthSpinner.setAdapter(adapter);
//
//        monthSpinner.setOnItemSelectedListener(this);

        AddButton = findViewById(R.id.AddButton);

        envelope = new Envelope();

        //onclick event for add envelope
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("SetupBudget");
                dbRefEnvName = dbRef.child("envName");

                try {
                    if (TextUtils.isEmpty(AddEnvelopeNameText.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Envelope Name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(AddBudgetAmountText.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Budget Amount", Toast.LENGTH_SHORT).show();


//                    else if (TextUtils.isEmpty(AddMonthPeriodText.getText().toString()))
//                        Toast.makeText(getApplicationContext(),"Please Enter Month Period", Toast.LENGTH_SHORT).show();

                    else {


                        envelope.setName(AddEnvelopeNameText.getText().toString().trim());
                        envelope.setAmount(Double.parseDouble(AddBudgetAmountText.getText().toString().trim()));
                        //envelope.setMonth(AddMonthPeriodText.getText().toString().trim());
                        envelope.setMonth(textMonthType);


                        dbRef.push().setValue(envelope);  //insert into database
                        //dbRef.child("envelope1").setValue(envelope); //create table name and insert data

                        //view the toast message
                        Toast.makeText(getApplicationContext(), "Data saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();

                        Intent n = new Intent(getApplicationContext(), MyEnvelope.class);
                        startActivity(n);


                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Budget Amount", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onClick(View view) {

    }


//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (adapterView.getSelectedItemPosition() > 0) {
//            itemPosition = adapterView.getSelectedItemPosition();
//            textMonthType = adapterView.getItemAtPosition(i).toString();
//            Toast.makeText(adapterView.getContext(), textMonthType + " selected", Toast.LENGTH_SHORT).show();
//        } else {
//            itemPosition = 0;
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }


}