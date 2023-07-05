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

    TextView mobileNumber, companyTxt, emailTxt, addressTxt, notesTxt;
    LinearLayout companyView, emailView, addressView, notesView;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //cardView
        companyView = findViewById(R.id.cardViewCompany);
        emailView = findViewById(R.id.cardViewEmail);
        addressView = findViewById(R.id.cardViewAddress);
        notesView = findViewById(R.id.cardViewNotes);

        //TextViews
        mobileNumber = findViewById(R.id.number);
        companyTxt = findViewById(R.id.Company);
        emailTxt = findViewById(R.id.email);
        addressTxt = findViewById(R.id.address);
        notesTxt = findViewById(R.id.notes);

        //Get Intent from Main Activity to display contact details in this activity
        String firstName = getIntent().getStringExtra(CreateNewContact.First_Name);
        String lastName = getIntent().getStringExtra(CreateNewContact.Last_Name);
        String company = getIntent().getStringExtra(CreateNewContact.Company);
        String phoneNumber = getIntent().getStringExtra(CreateNewContact.PhoneNumber);
        String email = getIntent().getStringExtra(CreateNewContact.Email);
        String address = getIntent().getStringExtra(CreateNewContact.Address);
        String notes = getIntent().getStringExtra(CreateNewContact.Notes);

        //FirstName and LastName
        if (lastName == null) {
            lastName = "";
        }
        collapsingToolbarLayout.setTitle(firstName + " " + lastName);
        mobileNumber.setText(phoneNumber);


        if (company == null) {
            companyView.setVisibility(View.GONE);

        } else {
            companyView.setVisibility(View.VISIBLE);
            companyTxt.setText(company);
        }

        if (email == null) {
            emailView.setVisibility(View.GONE);

        } else {
            emailView.setVisibility(View.VISIBLE);
            emailTxt.setText(email);
        }

        if (address == null) {
            addressView.setVisibility(View.GONE);

        } else {
            addressView.setVisibility(View.VISIBLE);
            addressTxt.setText(address);
        }
        if (notes == null) {
            notesView.setVisibility(View.GONE);

        } else {
            notesView.setVisibility(View.VISIBLE);
            notesTxt.setText(notes);
        }
    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        super.onBackPressed();
        finish();

    }
}