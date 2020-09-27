package com.mad_mini_project;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


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







}