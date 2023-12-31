package com.example.contact;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.contact.databinding.ActivityCreateNewContactBinding;

public class CreateNewContact extends AppCompatActivity {

    public static final String Contact_ID = "contact_Id";
    public static final String First_Name = "firstName";
    public static final String Last_Name = "lastName";
    public static final String Company = "company";
    public static final String PhoneNumber = "phoneNumber";
    CreateNewActivityClickHandler handler;
    ActivityCreateNewContactBinding createNewContactBinding;
    ContactViewModel contactViewModel;




    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((width), (int) (height * .95));

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



            if(contact.getFirstName() == null){
                Toast.makeText(CreateNewContact.this, "empty", Toast.LENGTH_SHORT).show();
            }else {

                contactViewModel.addNewContact(contact);
                Intent i = new Intent();
                i.putExtra(First_Name,contact.getFirstName());
                i.putExtra(Last_Name,contact.getLastName());
                i.putExtra(Company,contact.getCompany());
                i.putExtra(PhoneNumber,contact.getPhoneNumber());

                setResult(RESULT_OK,i);
                finish();

//                createView

            }

            Toast.makeText(CreateNewContact.this, "Done Click", Toast.LENGTH_SHORT).show();
        }
    }


}