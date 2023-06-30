package com.example.contact;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert
    long insertContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);


    @Query("select * from contact")
    LiveData<List<Contact>> getAllContact();

    @Query("select * from contact where id==:contact_id")
    Contact getContact(long contact_id);

}

