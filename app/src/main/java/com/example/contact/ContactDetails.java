package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ContactDetails extends AppCompatActivity {

    TextView  mobileNumber, companyTxt;
    LinearLayout companyView;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

//        getSupportActionBar().hide();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);





        mobileNumber = findViewById(R.id.number);
        companyTxt = findViewById(R.id.Company);
        companyView = findViewById(R.id.cardViewCompany);

        //Get Intent from Main Activity to display contact details in this activity
        String firstName = getIntent().getStringExtra(CreateNewContact.First_Name);
        String lastName = getIntent().getStringExtra(CreateNewContact.Last_Name);
        String company = getIntent().getStringExtra(CreateNewContact.Company);
        String phoneNumber = getIntent().getStringExtra(CreateNewContact.PhoneNumber);

        collapsingToolbarLayout.setTitle(firstName + " " + lastName);
        mobileNumber.setText(phoneNumber);


        if (company == null) {
            companyView.setVisibility(View.GONE);

        } else {
            companyView.setVisibility(View.VISIBLE);
            companyTxt.setText(company);
        }
    }
}