package com.mad_mini_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTransactions extends AppCompatActivity {


    String txtExtra;
    EditText name;
    EditText description;
    EditText amount;
    EditText date;
    Button cbtn, ibtn;
    Toast toast;
    Transactions tr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transactions);

        name = findViewById(R.id.txt1);
        description = findViewById(R.id.txt2);
        amount = findViewById(R.id.txt3);
        date = findViewById(R.id.txtDate);
        cbtn = findViewById(R.id.btn1);
        ibtn = findViewById(R.id.btn);
        Intent intent = getIntent();
        txtExtra = intent.getStringExtra("TransactionName");

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupTransaction").child(txtExtra);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    description.setText(dataSnapshot.child("description").getValue().toString());
                    amount.setText(dataSnapshot.child("amount").getValue().toString());
                    date.setText(dataSnapshot.child("date").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupTransaction").child(txtExtra);

                try {
                    if (TextUtils.isEmpty(name.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(description.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(amount.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(date.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Date", Toast.LENGTH_SHORT).show();

                    else {

                        dbRef.child("name").setValue(name.getText().toString());
                        dbRef.child("description").setValue(description.getText().toString());
                        dbRef.child("amount").setValue(Double.parseDouble(amount.getText().toString()));
                        dbRef.child("date").setValue(date.getText().toString());
                        System.out.println(tr);

                        Context context = getApplicationContext();
                        CharSequence text = "Transaction Updated Successfully";
                        int duration = Toast.LENGTH_SHORT;
                        toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                        Intent intent = new Intent(EditTransactions.this, My_Transactions.class);
                        startActivity(intent);
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Budget Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Transaction Updated Cancelled";
                int duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                Intent intent = new Intent(EditTransactions.this, My_Transactions.class);
                startActivity(intent);
            }
        });
    }
}



