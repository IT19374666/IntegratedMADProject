package com.mad_mini_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class My_Transactions extends AppCompatActivity {


    public Context context;
    ListView listView;
    String id;
    ArrayList<Transactions> transactionList;
    Transactions transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__transactions);

        context = this;
        listView = findViewById(R.id.listView);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SetupTransaction");
        ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactionList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    transactions = snapshot.getValue(Transactions.class);
                    transactions.setId(id);
                    transactionList.add(transactions);
                }
                TransactionAdapter adapter = new TransactionAdapter(My_Transactions.this,transactionList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SetupTransaction");

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                final Transactions transactions = transactionList.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                id = transactions.getId();

                builder.setTitle(transactions.getName());
                builder.setMessage("What do you want to do ? ");
                builder.setNeutralButton("UPDATE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(context, EditTransactions.class);
                        intent.putExtra("TransactionName", id);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTransaction();

                    }
                });
                builder.show();
            }
        });

        FloatingActionButton transactionbutton = findViewById(R.id.transactionbutton);
        transactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTransactions.class);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Transaction);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Account:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                    case R.id.Envelope:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                    case R.id.BillTracker:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                    case R.id.Transaction:
                        startActivity(new Intent(getApplicationContext(), My_Transactions.class));
                        return true;
                }



                return false;

            }
        });


    }

    public void deleteTransaction() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SetupTransaction");
        System.out.println(id);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id)) {
                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("SetupTransaction").child(id);
                    dbRef.removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}