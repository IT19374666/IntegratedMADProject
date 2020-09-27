package com.mad_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateEnvelope extends AppCompatActivity {

    String ExtraEditEnvelopeNameText;
    TextView EditEnvelopeNameText, EditBudgetAmountText;
    Button DeleteButton, UpdateButton;
    Envelope envelope;
    Integer itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_envelope);

        Intent intent = getIntent();
        ExtraEditEnvelopeNameText = intent.getStringExtra("EnvelopeName");

        //ExtraEditBudgetAmountText = intent.getStringExtra("Amount");
        //ExtraEditMonthPeriodText = intent.getStringExtra("Month");
        DeleteButton = findViewById(R.id.DeleteButton);
        UpdateButton = findViewById(R.id.UpdateButton);
        EditEnvelopeNameText = findViewById(R.id.EditEnvelopeNameText);
        EditBudgetAmountText = findViewById(R.id.EditBudgetAmountText);
        //EditMonthPeriodText = findViewById(R.id.EditMonthPeriodText);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupBudget").child(ExtraEditEnvelopeNameText);
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    EditEnvelopeNameText.setText(dataSnapshot.child("name").getValue().toString());
                    EditBudgetAmountText.setText(dataSnapshot.child("amount").getValue().toString());
                    //EditMonthPeriodText.setText(dataSnapshot.child("month").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupBudget").child(ExtraEditEnvelopeNameText);

                dbRef.removeValue();
                Toast.makeText(getApplicationContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateEnvelope.this,MyEnvelope.class));

            }
        });


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupBudget").child(ExtraEditEnvelopeNameText);

                try {
                    if (TextUtils.isEmpty(EditEnvelopeNameText.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter Envelope Name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(EditBudgetAmountText.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter Budget Amount", Toast.LENGTH_SHORT).show();


//                    else if (TextUtils.isEmpty(EditMonthPeriodText.getText().toString()))
//                        Toast.makeText(getApplicationContext(),"Please Enter Month Period", Toast.LENGTH_SHORT).show();

                    else{

                        dbRef.child("name").setValue( EditEnvelopeNameText.getText().toString());
                        dbRef.child("amount").setValue( Double.parseDouble(EditBudgetAmountText.getText().toString()));
                        //dbRef.child("name").setValue(ExtraEditEnvelopeNameText);

                        System.out.println(envelope);


                        //view the toast message
                        Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UpdateEnvelope.this, MyEnvelope.class);
                        startActivity(intent);


                    }

                }
                catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),"Invalid Budget Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}