package com.example.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.contact.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ContactViewModel contactViewModel;

    RecyclerView recyclerView;
    ArrayList<Contact> contactArrayList;

    ContactRecyclerAdapter contactRecyclerAdapter;
    ActivityMainBinding activityMainBinding;
    MainActivityClickEvent handler;
    public long selectedContactId;

    androidx.appcompat.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handler = new MainActivity.MainActivityClickEvent();
        activityMainBinding.setClickListenerMainActivity(handler);


        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();


        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                contactRecyclerAdapter.getFilter().filter(newText);

                return false;
            }
        });



        //ViewModel Calling
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        // Handling Swiping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = contactArrayList.get(viewHolder.getAdapterPosition());
                contactViewModel.deleteContact(contact);
            }
        }).attachToRecyclerView(recyclerView);


        contactViewModel.getGetContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                contactRecyclerAdapter = new ContactRecyclerAdapter((ArrayList<Contact>) contacts);
                contactRecyclerAdapter.setContact((ArrayList<Contact>) contacts);
                recyclerView.setAdapter(contactRecyclerAdapter);
                contactRecyclerAdapter.setListener(new ContactRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Contact contact) {
                        selectedContactId = contact.getId();

                        Intent i = new Intent(MainActivity.this, ContactDetails.class);
                        i.putExtra(CreateNewContact.Contact_ID, selectedContactId);
                        i.putExtra(CreateNewContact.First_Name, contact.getFirstName());
                        i.putExtra(CreateNewContact.Last_Name, contact.getLastName());
                        i.putExtra(CreateNewContact.Company, contact.getCompany());
                        i.putExtra(CreateNewContact.PhoneNumber, contact.getPhoneNumber());
                        startActivity(i);

                    }
                });

            }
        });
    }

//    public void LoadCoursesArrayList(long contactID) {
//        contactViewModel.getContactByID(contactID).observe(this, new Observer<List<Contact>>() {
//            @Override
//            public void onChanged(List<Contact> contacts) {
//                contactArrayList = (ArrayList<Contact>) contacts;
//                LoadRecyclerView();
//            }
//        });
//    }
//
//    private void LoadRecyclerView() {
//        recyclerView = activityMainBinding.recycleView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//
//        contactRecyclerAdapter = new ContactRecyclerAdapter();
//        recyclerView.setAdapter(contactRecyclerAdapter);
//
//        contactRecyclerAdapter.setContact(contactArrayList);


        // EDIT THE COURSE
//        contactRecyclerAdapter.setListener(new ContactRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Contact contact) {
//                selectedContactId = contact.getId();
//
//                Intent i = new Intent(MainActivity.this, ContactDetails.class);
////                i.putExtra(AddEditActivity.COURSE_ID, selectedCourseId);
////                i.putExtra(AddEditActivity.COURSE_NAME, course.getCourseName());
////                i.putExtra(AddEditActivity.UNIT_PRICE, course.getUnitPrice());
//                startActivity(i);
//
//            }
//        });
//    }

//    private void addNewContact(Contact contact) {
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                // OnBackground
//                contactViewModel.addNewContact(contact);
//                contactArrayList.add(contact);
//
//                // On Post Execution
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        contactRecyclerAdapter.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        });
//
//    }

    public class MainActivityClickEvent {
        public void onFabBtnClick(View view) {
            Intent i = new Intent(getApplicationContext(), CreateNewContact.class);
            startActivity(i);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            String firstName = data.getStringExtra(CreateNewContact.First_Name);
            String lastName = data.getStringExtra(CreateNewContact.Last_Name);
            String company = data.getStringExtra(CreateNewContact.Company);
            String phoneNumber = data.getStringExtra(CreateNewContact.PhoneNumber);

            Contact contact = new Contact(0, firstName, lastName, company, phoneNumber);
            contactViewModel.addNewContact(contact);


        }
    }

}