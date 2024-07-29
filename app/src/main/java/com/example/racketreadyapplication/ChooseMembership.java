package com.example.racketreadyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ChooseMembership extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Membership> memberships;
    TextView isCompetitive;
    TextView isCourtBooking;
    TextView isTournament;
    TextView membershipDuration;
    TextView membershipPrice;
    Button btnSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_membership);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnBack = findViewById(R.id.btnChooseBack);
        btnSelect = findViewById(R.id.btnSelectMembership);
        isCompetitive = findViewById(R.id.tvIsComp);
        isCourtBooking = findViewById(R.id.tvIsCourtBooking);
        isTournament = findViewById(R.id.tvIsTournament);
        membershipDuration = findViewById(R.id.tvMemDays);
        membershipPrice = findViewById(R.id.tvMemPrice);
        Spinner spinMemberships = findViewById(R.id.spinMemberships);
        spinMemberships.setOnItemSelectedListener(this);
        DatabaseHelper db = new DatabaseHelper(this);
        memberships = db.getMemberships();
        ArrayList<String> membershipNames = new ArrayList<>();
        for (Membership m : memberships){
            membershipNames.add(m.getMembershipName());
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, membershipNames);
        spinMemberships.setAdapter(adapter);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Membership selectedMembership;
                String selectedMem = spinMemberships.getSelectedItem().toString();
                int selectedIndex = membershipNames.indexOf(selectedMem);
                Intent b = new Intent(ChooseMembership.this, UpgradeMembership.class);
                selectedMembership = memberships.get(selectedIndex);
                b.putExtra("ChosenMembership", selectedMembership);
                startActivity(b);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMembership.this, ManageMembership.class));
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Membership m = memberships.get(pos);
        String days = m.getDuration() + " Days";
        membershipDuration.setText(days);
        NumberFormat formatter = new DecimalFormat("$###.##");
        String price = formatter.format(m.getMembershipPrice());
        membershipPrice.setText(price);
        String[] modes = new String[]{"Enabled", "Disabled"};

        if (m.isEnableCompetitive()){
            isCompetitive.setText(modes[0]);
        }else{
            isCompetitive.setText(modes[1]);
        }

        if (m.isEnableCourtBooking()){
            isCourtBooking.setText(modes[0]);
        }else{
            isCourtBooking.setText(modes[1]);
        }

        if (m.isEnableTournamentCreation()){
            isTournament.setText(modes[0]);
        }else{
            isTournament.setText(modes[1]);
        }

        if (m.getMembershipName().equals("Standard")){
            btnSelect.setVisibility(View.GONE);
        }else{
            btnSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}