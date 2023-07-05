package com.example.contact;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class ContactViewModel extends AndroidViewModel {

    private final ContactRepo contactRepo;
    private LiveData<List<Contact>> getContact;
    private LiveData<List<Contact>> getContactByID;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepo = new ContactRepo(application);
    }

    public LiveData<List<Contact>> getGetContact() {
        getContact = contactRepo.getAllContact();
        return getContact;
    }

    public LiveData<List<Contact>> getContactByID(long contactID) {
        getContactByID = contactRepo.getContactByID(contactID);
        return getContactByID(contactID);
    }

    public void addNewContact(Contact contact) {
        contactRepo.insertContact(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepo.deleteContact(contact);
    }

    public void updateContact(Contact contact) {
        contactRepo.updateContact(contact);
    }
}

//    Finally we will reference the viewmodel in our view (Activity or fragment) and call respective CRUD Functions

//noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class); //init
//        noteViewModel.insert(item); //insert
//        noteViewModel.delete(item); // delete
//        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
//@Override
//public void onChanged(List<Note> notes) { //called every time data changes
//
//        }
//        }); //get all data
//        noteViewModel.update(item); //update
//        noteViewModel.deleteAllNotes(); //delete all data
