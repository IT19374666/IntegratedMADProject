package com.mad_mini_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NotifyMessage extends AppCompatActivity {

    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the notification message to textview
        setContentView(R.layout.notification_message);
        txtView = findViewById(R.id.notify_message);
        Bundle bundle = getIntent().getExtras();
        txtView.setText(bundle.getString("message"));
    }
}
