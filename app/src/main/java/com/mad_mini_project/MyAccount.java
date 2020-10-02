package com.mad_mini_project;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


        /*FloatingActionButton floatingActionButtonbutton = findViewById(R.id.addAccBtn);
        floatingActionButtonbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAddAcount();
            }
        });*/

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Account);
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
                        startActivity(new Intent(getApplicationContext(), BillSchedule.class));
                        return true;
                    case R.id.Transaction:
                        startActivity(new Intent(getApplicationContext(), MyAccount.class));
                        return true;
                }



                return false;

            }
        });



    }







}