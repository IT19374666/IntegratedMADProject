package com.mad_mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String extraAccName;
    String textAccType;
    Button btnSave;
    ImageButton btnDelete;
    Account account;
    TextView textAccName;
    EditText editAccountBalance;
    Spinner s;
    Integer itemPosition;
   // DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);


        Intent intent = getIntent();
        extraAccName = intent.getStringExtra("AccountName");
        btnSave = findViewById(R.id.saveAccountBtn);
        btnDelete = findViewById(R.id.acc_delete_btn);
        textAccName = findViewById(R.id.text_acc_name);
        editAccountBalance = findViewById(R.id.editAccountBalance);

        String[] arraySpinner = new String[]{
                "Choose Account Type....", "Savings", "Credit card","Cash"
        };


        s = (Spinner) findViewById(R.id.accTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Account").child(extraAccName);

        dbRef.addValueEventListener(new ValueEventListener() {
            /*this.accName = accName;
        this.accType = accType;
        this.balance = balance;*/
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        textAccName.setText(dataSnapshot.child("accName").getValue().toString());
                        editAccountBalance.setText(dataSnapshot.child("balance").getValue().toString());

                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }





        });





        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Account").child(extraAccName);

                try {



                    if (TextUtils.isEmpty(editAccountBalance.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter the account balance", Toast.LENGTH_SHORT).show();
                    } else if (itemPosition == 0){
                        Toast toast =  Toast.makeText(getApplicationContext(), "Please select the account type", Toast.LENGTH_SHORT);
                        View toastView = toast.getView(); // This'll return the default View of the Toast.

                        /* And now you can get the TextView of the default View of the Toast. */
                        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                        //toastMessage.setTextSize(18);
                        //toastMessage.setTextColor(Color.RED);
                        toastMessage.setGravity(Gravity.CENTER);
                        //toastView.setBackgroundColor(Color.YELLOW);
                        toastMessage.setCompoundDrawablePadding(16);
                        toast.show();

                    } else{
                        //Assigning input values to the account object variables


                        dbRef.child("accType").setValue(textAccType);
                        dbRef.child("balance").setValue( Double.parseDouble(editAccountBalance.getText().toString()));
                        System.out.println(account);

                        //Feedback
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(UpdateAccount.this, MyAccount.class);
                        startActivity(intent);


                    }

                } catch(Exception ex){
                    System.out.println("Cannot update");

                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Account").child(extraAccName);
                dbRef.removeValue();
                startActivity(new Intent(UpdateAccount.this,MyAccount.class));
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getSelectedItemPosition()> 0) {
            itemPosition = adapterView.getSelectedItemPosition();
            String text = adapterView.getItemAtPosition(i).toString();
            textAccType  = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), text + " selected", Toast.LENGTH_SHORT).show();
        }
        else{
            itemPosition = 0;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}