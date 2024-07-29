package com.example.racketreadyapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpgradeMembership extends AppCompatActivity {

    LocalDate date = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upgrade_membership);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        Membership newMembership = (Membership) i.getSerializableExtra("ChosenMembership");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userID = sharedPreferences.getInt("userID", 0);
        TextView tvTitle = findViewById(R.id.tvUpgradeTo);
        TextView tvPrice = findViewById(R.id.tvDisplayPrice);
        Button btnBack = findViewById(R.id.btnUpgradeBack);
        Button btnApply = findViewById(R.id.btnNewMembership);
        EditText edtCreditNum = findViewById(R.id.edtCreditNum);
        EditText edtExpireDate = findViewById(R.id.edtExpireDate);
        EditText edtCVV = findViewById(R.id.edtNumOnTheBack);
        TextView tvExpires = findViewById(R.id.tvExpiresIn);
        DatabaseHelper db = new DatabaseHelper(this);
        assert newMembership != null;
        String title = "Upgrade to " + newMembership.getMembershipName();
        NumberFormat formatter = new DecimalFormat("$###.##");
        String price = formatter.format(newMembership.getMembershipPrice());
        tvPrice.setText(price);
        tvTitle.setText(title);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now().plusDays(newMembership.getDuration());
            tvExpires.setText(date.toString());
        }
        // Back Button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpgradeMembership.this, ChooseMembership.class));
            }
        });
        // Upgrade Button
            // Input for Credit Card ##
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> flags = new ArrayList<>();
                String creditNum = edtCreditNum.getText().toString();
                if (creditNum.isEmpty()){
                    flags.add("Please insert a credit card number");
                }
                if (creditNum.length() != 16){
                    flags.add("Invalid Credit Card Number");
                }
                String expireDate = edtExpireDate.getText().toString();
                if (expireDate.isEmpty()){
                    flags.add("Please insert an expiration date");
                }
                if (!expireDate.contains("/") || expireDate.length() != 5){
                    flags.add("Invalid Expiration Date!");
                }
                String cvv = edtCVV.getText().toString();
                if (cvv.isEmpty()){
                    flags.add("Please insert a cvv");
                }
                if (cvv.length() != 3){
                    flags.add("Invalid CVV");
                }
                if (flags.isEmpty()){
                    db.updateMembership(userID,newMembership.getMembershipName(),date.toString(),newMembership.getMembershipPrice());
                    Toast.makeText(UpgradeMembership.this, "Added Membership Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UpgradeMembership.this, ManageMembership.class));
                }else{
                    for(String error : flags){
                        Toast.makeText(UpgradeMembership.this, error, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

            // Input for Credit Card Expiration Date

            // Input for Credit Card Numbers on the back

            // Boolean to check the values of the numbers, if they are matching with any existing cards

            // Boolean to check the value of the date, making sure it is correct in it's format

            // Boolean to check the 3 numbers on the back to make sure they are numbers

            // Boolean to check if the card does exist with the credentials given

            // Boolean turned insert statement, which inserts/updates a new membership for the user in MembershipStatus, with the amount paid and the expiration date depending
    }
}