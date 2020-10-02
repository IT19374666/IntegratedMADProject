package com.mad_mini_project;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BillSchedule extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private String key = "";
    private String title;
    private String amount;
    private String description;
    private String date2;
    private String time;

    private TextView timeView;
    private TextView dateView;
    private TextView updateDuedate;
    private TextView updateduetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        progressDialog = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bills");

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewBill();
            }
        });
    }

    //add new bill
    private void AddNewBill() {
        AlertDialog.Builder Ndialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View Nview = inflater.inflate(R.layout.add_bill, null);
        Ndialog.setView(Nview);

        final AlertDialog dialog = Ndialog.create();
        dialog.setCancelable(false);
       // dialog.show();

        setAlarm(date2,time);

        final EditText title = Nview.findViewById(R.id.title);
        final EditText amount = Nview.findViewById(R.id.amount);
        final EditText description = Nview.findViewById(R.id.description);

        //select date of bill to be paid
        Button dateBtn = Nview.findViewById(R.id.dateBtn);
        //select time to remind
        Button timeBtn = Nview.findViewById(R.id.timeBtn);
        //save bill
        Button save = Nview.findViewById(R.id.SaveBtn);
        //cancel adding bill
        Button cancel = Nview.findViewById(R.id.CancelBtn);
        timeView = Nview.findViewById(R.id.timeView);
        dateView = Nview.findViewById(R.id.dateView);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });
        
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(TextUtils.isEmpty(title.getText().toString()))
//                    Toast.makeText(getApplicationContext(), "Please Enter Bill Title!", Toast.LENGTH_SHORT).show();
//                else if(TextUtils.isEmpty(amount.getText().toString()))
//                    Toast.makeText(getApplicationContext(), "Please Enter Bill Amount!", Toast.LENGTH_SHORT).show();
//                else if(TextUtils.isEmpty(description.getText().toString()))
//                    Toast.makeText(getApplicationContext(), "Please Enter Bill Description!", Toast.LENGTH_SHORT).show();

                String Billtitle = title.getText().toString().trim();
                String Billamount = amount.getText().toString().trim();
                String Billdescription = description.getText().toString().trim();
                String id = databaseReference.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());

                //check whether fileds are empty or not
                if(TextUtils.isEmpty(Billtitle)){
                    title.setError("Title Required");
                    return;
                }
                if(TextUtils.isEmpty(Billamount)){
                    amount.setError("Amount of Bill is Required");
                    return;
                }
                if(TextUtils.isEmpty(Billdescription)){
                    description.setError("Description for bill is Required");
                    return;
                } else {
                    progressDialog.setMessage("Adding your Bill details");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    //setAlarm(date2,time);
                    //System.out.println(date2 + "," +time);
                    AddBill addBill = new AddBill(Billtitle,Billamount,Billdescription,id,date,date2,time);

                    databaseReference.child(id).setValue(addBill).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(BillSchedule.this, "Bill has been added ",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(BillSchedule.this, "Failed" + error,Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
//    timeView =(TextView) findViewById(R.id.timeView);
    //select time function
    private void handleTimeButton() {

        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        //boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//                String timeShow = hourOfDay + ":" + minute;
//                timeView.setText(timeShow);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR,hourOfDay);
                calendar1.set(Calendar.MINUTE,minute);
                //time format
                CharSequence charSequence = android.text.format.DateFormat.format("hh:mm a", calendar1);
                timeView.setText(charSequence);
                time = charSequence.toString();
            }
        },HOUR,MINUTE,false);

        timePickerDialog.show();
        //Toast.makeText(this,"date",Toast.LENGTH_SHORT).show();
    }

    //select date function
    private void handleDateButton() {

        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

             //   String dateShow = dayOfMonth +"/"+ month +"/"+ year;
             //   dateView.setText(dateShow);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,dayOfMonth);
                //select date format
                CharSequence charSequence = android.text.format.DateFormat.format("MMM d, yyyy",calendar1);
                dateView.setText(charSequence);
                date2 = charSequence.toString();
            }
        },YEAR,MONTH,DATE);

        datePickerDialog.show();
        //Toast.makeText(this,"time",Toast.LENGTH_SHORT).show();
    }
      //set reminder on specific time
    private void setAlarm(String date2, String time){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(),ReminderBroadcast.class);
        intent.putExtra("date",date2);
        intent.putExtra("time",time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);

        String dateandTime = date2 + "" + time;
        DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy hh:mm");
       try {
           Date date1 = dateFormat.parse(dateandTime);
           alarmManager.set(AlarmManager.RTC_WAKEUP,date1.getTime(),pendingIntent);

       } catch (ParseException e){
           e.printStackTrace();
       }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //retrieve from database and add data to recyclerview as cards
        FirebaseRecyclerOptions<AddBill> options = new FirebaseRecyclerOptions.Builder<AddBill>()
                .setQuery(databaseReference, AddBill.class)
                .build();

        FirebaseRecyclerAdapter<AddBill, NewViewHolder> adapter = new FirebaseRecyclerAdapter<AddBill, NewViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NewViewHolder holder, final int position, @NonNull final AddBill model) {

                holder.setTitle(model.getbTitle());
                holder.setAmount(model.getbAmount());
                holder.setDescription(model.getbDescription());
                holder.setDate(model.getbDate());
                holder.setDueDate(model.getSelDate());
                holder.setDueTime(model.getBtime());

                //show update bill function by clicking card
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        key = getRef(position).getKey();
                        key = model.getbId();
                        title = model.getbTitle();
                        amount = model.getbAmount();
                        description = model.getbDescription();
                        date2 = model.getSelDate();
                        time = model.getBtime();

                        updateBill();
                    }
                });
            }

            @NonNull
            @Override
            public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_bill,parent,false);
                return new NewViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    //show cards at recyclerview with details
    public static class NewViewHolder extends RecyclerView.ViewHolder{

        View NwView;
        CardView cardView;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            NwView = itemView;
            cardView = NwView.findViewById(R.id.parent_layout);
        }
        public void setTitle(String title){
            TextView titleTextview = NwView.findViewById(R.id.showTitle);
            titleTextview.setText(title);
        }
        public void setAmount(String amount){
            TextView amountTextview = NwView.findViewById(R.id.showAmount);
            amountTextview.setText(amount);
        }
        public void setDescription(String description){
            TextView descriptionTextview = NwView.findViewById(R.id.showDescription);
            descriptionTextview.setText(description);
        }
        public void setDate(String date){
            TextView dateTextView = NwView.findViewById(R.id.showDate);
            dateTextView.setText(date);
        }
        public void setDueDate(String date2){
            TextView date2TextView = NwView.findViewById(R.id.showDueDate);
            date2TextView.setText(date2);
        }
        public void setDueTime(String time){
            TextView timeTextView = NwView.findViewById(R.id.showDueTime);
            timeTextView.setText(time);
        }

    }

    //update bill function
    private void updateBill(){
        AlertDialog.Builder newdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_bill, null);
        newdialog.setView(view);

        Button updatedateBtn = view.findViewById(R.id.updatedateBtn);
        Button updatetimeBtn = view.findViewById(R.id.updatetimeBtn);
        updateduetime = view.findViewById(R.id.updateduetime);
        updateDuedate = view.findViewById(R.id.updateDuedate);

        final AlertDialog dialog = newdialog.create();

        final EditText Etitle = view.findViewById(R.id.editTitle);
        final EditText Eamount = view.findViewById(R.id.editAmount);
        final EditText Edescription = view.findViewById(R.id.editDescription);

        Etitle.setText(title);
        Etitle.setSelection(title.length());

        Eamount.setText(amount);
        Eamount.setSelection(amount.length());

        Edescription.setText(description);
        Edescription.setSelection(description.length());

        Button deleteBtn = view.findViewById(R.id.DeleteBtn);
        Button updateBtn = view.findViewById(R.id.UpdateBtn);

        updatedateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDateButton();
            }
        });

        updatetimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTimeButton();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = Etitle.getText().toString().trim();
                amount = Eamount.getText().toString().trim();
                description = Edescription.getText().toString().trim();

                String date = DateFormat.getDateInstance().format(new Date());

                AddBill model = new AddBill(title,amount,description,key,date,date2,time);

                databaseReference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(BillSchedule.this,"Data Has Been Updated Successfully!",Toast.LENGTH_SHORT).show();
                        } else {

                            String error = task.getException().toString();
                            Toast.makeText(BillSchedule.this,"Update Failed!" +error ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BillSchedule.this,"Bill Deleted Successfully!",Toast.LENGTH_SHORT).show();
                        } else{
                            String error = task.getException().toString();
                            Toast.makeText(BillSchedule.this,"Delete Unsuccessfull!"+error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        dialog.show();

    }
    //update selected time
    private void UpdateTimeButton() {

        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        //boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR,hourOfDay);
                calendar1.set(Calendar.MINUTE,minute);

                CharSequence charSequence = android.text.format.DateFormat.format("hh:mm a", calendar1);
                updateduetime.setText(charSequence);
                time = charSequence.toString();
            }
        },HOUR,MINUTE,false);
        timePickerDialog.show();
    }
    //update selected date
    private void UpdateDateButton() {

        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,dayOfMonth);

                CharSequence charSequence = android.text.format.DateFormat.format("MMM d, yyyy",calendar1);
                updateDuedate.setText(charSequence);
                date2 = charSequence.toString();
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();
    }
}
