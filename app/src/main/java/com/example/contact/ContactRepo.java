package com.example.contact;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepo {
    private final ContactDAO contactDao;
    private final LiveData<List<Contact>> allContact;

    public ContactRepo(Application application) {
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        contactDao = contactDatabase.getContactDAO();
        allContact = contactDao.getAllContact();

    }

    //Methods for Database operation
    public void insertContact(Contact contact) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.insertContact(contact);
            }
        });
    }

    public void updateContact(Contact contact) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.updateContact(contact);
            }
        });
    }

    public void deleteContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Inserting Categories
                contactDao.deleteContact(contact);
                // Do after background execution is done - post execution
            }
        });
    }

    public LiveData<List<Contact>> getAllContact() {
        return allContact;
    }

    public LiveData<List<Contact>> getContactByID(long contactID) {
        return getContactByID(contactID);
    }
}

