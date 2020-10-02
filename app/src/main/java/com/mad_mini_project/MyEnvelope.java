package com.mad_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyEnvelope extends AppCompatActivity {

    public Context context;
    Envelope envelope;
    ListView listView;
    ArrayList<Envelope> envelopeList;
    String envKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envelope);

        context = this;

        listView = findViewById(R.id.listView);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SetupBudget");
        ref.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                envelopeList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String envkey = snapshot.getKey();
                    envelope = snapshot.getValue(Envelope.class);
                    envelope.setEnvkey(envkey);
                    envelopeList.add(envelope);
                }
                EnvelopeAdapter adapter = new EnvelopeAdapter(MyEnvelope.this,envelopeList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SetupBudget");


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                final Envelope envelope = envelopeList.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                envKey = envelope.getEnvkey();

                builder.setTitle(envelope.getName());
                builder.setMessage("What do you want to do ? ");
                builder.setNeutralButton("UPDATE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intent = new Intent(getApplicationContext(), UpdateEnvelope.class);


                        Intent intent = new Intent(context,UpdateEnvelope.class);
                        intent.putExtra("EnvelopeName", envKey);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEnvelope();

                    }
                });
                builder.show();
            }



        });


        FloatingActionButton add_envelope_button = findViewById(R.id.add_envelope_button);
        add_envelope_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddEnvelope.class);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Envelope);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Account:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                    case R.id.Envelope:
                        startActivity(new Intent(getApplicationContext(), MyEnvelope.class));
                        return true;
                    case R.id.BillTracker:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                    case R.id.Transaction:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                }

                return false;

            }
        });


    }

    public void deleteEnvelope() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SetupBudget");
        System.out.println(envKey);
        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(envKey)){
                        Toast.makeText(getApplicationContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show();
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupBudget").child(envKey);
                        dbRef.removeValue();
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

}