package com.example.callingapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.callingapp.db.entity.Contacts;

@Database(entities = {Contacts.class},version = 1)
public abstract class ContactsDatabase extends RoomDatabase {
     public abstract DatabaseDao GetDao();
}