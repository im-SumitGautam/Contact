package com.example.contact;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.contact.databinding.ActivityCreateNewContactBinding;

public class CreateNewContact extends AppCompatActivity {

    public static final String Contact_ID = "contact_Id";
    public static final String First_Name = "firstName";
    public static final String Last_Name = "lastName";
    public static final String Company = "company";
    public static final String PhoneNumber = "phoneNumber";
    public static final String Email = "email";
    public static final String Address = "address";
    public static final String Notes = "notes";
    CreateNewActivityClickHandler handler;
    ActivityCreateNewContactBinding createNewContactBinding;
    ContactViewModel contactViewModel;
    Contact contact;

    Animation btnClickAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((width), (int) (height * .90));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.x = 0;
        layoutParams.y = -20;

        getWindow().setAttributes(layoutParams);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        contact = new Contact();
        createNewContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_contact);
        handler = new CreateNewActivityClickHandler(this);
        createNewContactBinding.setCreateNewContactClickHandler(handler);

        createNewContactBinding.setContactEditText(contact);

        btnClickAnimation = AnimationUtils.loadAnimation(this, R.anim.btn_click);

    }

    public class CreateNewActivityClickHandler {
        Context context;

        public CreateNewActivityClickHandler(Context context) {
            this.context = context;
        }

        public void onCancelTxtClick(View view) {
            finish();
        }

        public void onDoneTxtClick(View view) {

            //set the contact
            if (contact.getFirstName() == null) {
                btnClickAnimation.start();
                Toast.makeText(CreateNewContact.this, "empty", Toast.LENGTH_SHORT).show();
            } else {
                contactViewModel.addNewContact(contact);
                Intent i = new Intent();
                i.putExtra(First_Name, contact.getFirstName());
                i.putExtra(Last_Name, contact.getLastName());
                i.putExtra(Company, contact.getCompany());
                i.putExtra(PhoneNumber, contact.getPhoneNumber());
                i.putExtra(Email, contact.getEmail());
                i.putExtra(Address, contact.getAddress());
                i.putExtra(Notes, contact.getNote());

                setResult(RESULT_OK, i);
                finish();
            }
            btnClickAnimation.start();
            Toast.makeText(CreateNewContact.this, "Done Click", Toast.LENGTH_SHORT).show();
        }
    }
}