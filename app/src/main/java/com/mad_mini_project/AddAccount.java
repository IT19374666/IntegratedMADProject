package com.mad_mini_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText textAccName;
    EditText textAccBalance;
    String[] accArraySpinner;
    String textAccType = null;
    Spinner accSpinner;
    Button btnBack, btnSave;
    DatabaseReference dbRef, dbRefAccName;
    Account account;
    Integer itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        textAccName = findViewById(R.id.accountName);
        textAccBalance = findViewById(R.id.editAccountBalance);

        //Implementing the spinner
        accArraySpinner = new String[]{ "Choose Account Type....", "Savings", "Credit card","Cash", "Other" };
        accSpinner = (Spinner) findViewById(R.id.accTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accArraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accSpinner.setAdapter(adapter);
        accSpinner.setOnItemSelectedListener(this);



        //Get reference to the button views
        btnSave = findViewById(R.id.saveAccountBtn);

        //create Account object
        account = new Account();




        //Implement the onclick event for save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Account");
                dbRefAccName = dbRef.child("accName");
                try {
                    if(itemPosition == 0){
                        Toast toast =  Toast.makeText(getApplicationContext(), "Please select the account type", Toast.LENGTH_SHORT);
                        View toastView = toast.getView(); // This'll return the default View of the Toast.

                        /* And now you can get the TextView of the default View of the Toast. */
                        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                        //toastMessage.setTextSize(18);
                        toastMessage.setGravity(Gravity.CENTER);
                        //toastView.setBackgroundColor(Color.YELLOW);
                        toastMessage.setCompoundDrawablePadding(16);
                        toast.show();

                    }


                    else if (TextUtils.isEmpty(textAccName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter a unique account name", Toast.LENGTH_SHORT).show();
                    }

                    else if (TextUtils.isEmpty(textAccBalance.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter the account balance", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        //Assigning input values to the account object variables
                        account.setAccName( textAccName.getText().toString());
                        account.setAccType(textAccType);
                        account.setBalance( Double.parseDouble(textAccBalance.getText().toString()));

                        //Insert to the db
                        dbRef.push().setValue(account);

                        //Feedback
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(AddAccount.this, MyAccount.class);
                        startActivity(intent);


                    }

                } catch(Exception ex){

                }


            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getSelectedItemPosition()> 0) {
            itemPosition = adapterView.getSelectedItemPosition();
            textAccType  = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), textAccType + " selected", Toast.LENGTH_SHORT).show();
        }
        else{
            itemPosition = 0;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}