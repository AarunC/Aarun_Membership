package com.example.racketreadyapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.prefs.Preferences;

public class ManageMembership extends AppCompatActivity {

    String membershipName = "";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_membership);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnBack = findViewById(R.id.btnManageMemBack);
        Button btnMem = findViewById(R.id.btnUpgradeMembership);
        Button btnCancel = findViewById(R.id.btnCancelMembership);
        TextView memName = findViewById(R.id.tvMembershipName);
        TextView expirationDate = findViewById(R.id.tvExpirationDate);
        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Cursor c = db.viewMembership(sharedPreferences.getInt("userID",0));
        while (c.moveToNext()){
            membershipName = c.getString(1);
            memName.setText(membershipName);
            String expDate = c.getString(2);
            if (expDate.equals("None")){
                expirationDate.setText("Cannot Expire");
            }else{
                expirationDate.setText(expDate);
            }
            if (!membershipName.equals("Standard")){
                btnMem.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
            }else{
                btnMem.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
            }
        }
        // Button for Upgrading Membership
        btnMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMembership.this, ChooseMembership.class));
            }
        });
        // Button for Cancelling Membership
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (membershipName.equals("Standard")){
                    Toast.makeText(ManageMembership.this, "Cannot cancel Standard Membership!", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(ManageMembership.this, Cancellation.class));
                }

            }
        });
            // Button should handle cancellation in 1 tap
            // Button should prompt the user to be sure on the cancellation (Are you sure you want to cancel?)
            // If yes, update/delete the membership from the MembershipStatus table
            // If membership is already Standard, prompt the user that they cannot or make the button unavailable

        // Button for Going Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMembership.this, UserProfile.class));
            }
        });
    }
}