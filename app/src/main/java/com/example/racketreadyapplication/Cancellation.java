package com.example.racketreadyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cancellation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cancellation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBack = findViewById(R.id.btnCancelBack);
        Button btnConfirm = findViewById(R.id.btnConfirmCancel);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userID = sharedPreferences.getInt("userID", 0);

        DatabaseHelper db = new DatabaseHelper(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cancellation.this, ManageMembership.class));
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean u = db.updateMembership(userID,"Standard","None",0);
                if (u){
                    Toast.makeText(Cancellation.this, "Successfully ended Membership", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Cancellation.this, ManageMembership.class));
                }else{
                    Toast.makeText(Cancellation.this, "Error with updating membership. Please try again at another time", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}