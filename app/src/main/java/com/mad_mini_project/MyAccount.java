package com.mad_mini_project;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MyAccount extends AppCompatActivity {
    public Context context;
    int image[] = {R.drawable.creaditwallet,R.drawable.moneywallet,R.drawable.savingwallet,R.drawable.otherwallet};

    Account account;
    ListView listView;
    TextView totText;
    ArrayList<Account> accountList;
    String accKey;
    //Double total = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        context = this;

        listView = findViewById(R.id.list_View);
        totText = findViewById(R.id.text_tot_amnt);
        totText = findViewById(R.id.text_tot_amnt);


        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Account");


        ref.addValueEventListener(new ValueEventListener() {
            Double total = 0.00;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accountList = new ArrayList<>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String key = dsp.getKey();

                    account = dsp.getValue(Account.class);

                    total = calcTotal(total, account.getBalance());

                    account.setKey(key);

                    accountList.add(account);
                    System.out.println(total);
                    System.out.println("Key"+ key);


                }

                AccountAdapter adapter = new AccountAdapter(MyAccount.this,accountList);
                listView.setAdapter(adapter);
                totText.setText(String.valueOf(total));



            }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String accName;
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Account");



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Account account = accountList.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                accKey = account.getKey();
                builder.setTitle(account.getAccName());
                builder.setMessage("What do you want to do? ");
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //accName = account.getAccName();
                        Intent intent = new Intent(context,UpdateAccount.class);
                        intent.putExtra("AccountName", accKey );
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deleteAccount();

                    }
                });

                builder.show();
            }

        });



        FloatingActionButton floatingActionButtonbutton = findViewById(R.id.addAccBtn);
        floatingActionButtonbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAddAcount();
            }
        });


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

                        //startActivity(new Intent(getApplicationContext(), AddAccount.class));

                        return true;
                    case R.id.BillTracker:
                        startActivity(new Intent(getApplicationContext(), AddAccount.class));
                        return true;
                    case R.id.Transaction:
                        startActivity(new Intent(getApplicationContext(), AddAccount.class));
                        return true;
                }



                return false;

            }
        });



    }

    public void deleteAccount(){
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Account");
        System.out.println(accKey);
        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(accKey)){
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Account").child(accKey);
                    dbRef.removeValue();
                    Toast.makeText(getApplicationContext(),"Account Deleted Successfully", Toast.LENGTH_SHORT);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Account Deleted Successfully", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }

    public void openAddAcount() {
        Intent intent = new Intent(this, AddAccount.class);
        startActivity(intent);


    }
    public double calcTotal(Double tot, Double balance){
        tot = tot + balance;
        return tot;
    }





}