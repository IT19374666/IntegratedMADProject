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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTransactions extends AppCompatActivity {

    Button btn, btn1;
    Toast toast;
    EditText txtName, txtDescription, txtAmount, txtDate;
    DatabaseReference dbRef;
    Transactions tr;

    private void clearControls() {
        txtName.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        txtDate.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        btn = findViewById(R.id.btn1);
        btn1 = findViewById(R.id.btn);
        txtName = findViewById(R.id.txt1);
        txtDescription = findViewById(R.id.txt2);
        txtAmount = findViewById(R.id.txt3);
        txtDate = findViewById(R.id.txtDate);
        tr = new Transactions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("SetupTransaction");
                try {
                    if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtAmount.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtDate.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Date", Toast.LENGTH_SHORT).show();

                    else {

                        tr.setName(txtName.getText().toString().trim());
                        tr.setDescription(txtDescription.getText().toString().trim());
                        tr.setAmount(Double.parseDouble(txtAmount.getText().toString().trim()));
                        tr.setDate(txtDate.getText().toString().trim());



                        dbRef.push().setValue(tr);  //insert into database

                        Context context = getApplicationContext();
                        CharSequence text = "Transaction Added Successfully";
                        int duration = Toast.LENGTH_SHORT;
                        toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                        clearControls();
                        Intent intent = new Intent(AddTransactions.this, My_Transactions.class);
                        startActivity(intent);

                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Amount", Toast.LENGTH_SHORT).show();
                }
            }
            });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Transaction Cancelled";
                int duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                Intent intent = new Intent(AddTransactions.this, My_Transactions.class);
                startActivity(intent);
            }
        });

    }
}





