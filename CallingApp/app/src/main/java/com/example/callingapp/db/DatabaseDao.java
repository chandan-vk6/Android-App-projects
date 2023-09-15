package com.example.callingapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.callingapp.db.entity.Contacts;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Insert
    public long CreateContact(Contacts contacts);

    @Update
    public void UpdateContact(Contacts contacts);

    @Delete
    public void DeleteContact(Contacts contacts);

    @Query("select * from contacts")
    public List<Contacts> GetAllContacts();

    @Query("select * from contacts where contact_id==:id")
    public  Contacts GetContact(long id);


}
