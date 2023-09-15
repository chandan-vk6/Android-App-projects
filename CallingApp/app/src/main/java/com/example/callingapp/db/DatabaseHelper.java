/* package com.example.callingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.callingapp.db.entity.Contacts;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static  final String DATABASE_NAME = "contacts_db";
    private static  final int DATABASE_VERSION = 1;

    public  DatabaseHelper(Context context){
        super(context,DATABASE_NAME,  null,  DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Contacts.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + Contacts.TABLE_NAME);
            onCreate(sqLiteDatabase);
    }

    public long insert(String name , String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contacts.CONTACTS_NAME,name);
        values.put(Contacts.CONTACT_EMAIL,email);

        long id = db.insert(Contacts.TABLE_NAME,null,values);
        return id;
    }

    public Contacts getcontact(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Contacts.TABLE_NAME,
                                new String[]{
                                        Contacts.CONTACTS_ID,
                                        Contacts.CONTACTS_NAME,
                                        Contacts.CONTACT_EMAIL},
                                        Contacts.CONTACTS_ID + "=?",
                              new String[]{
                                      String.valueOf(id)
                              },
                null,
                 null,
                null,
                  null
                );
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Contacts contacts = new Contacts(
                cursor.getInt(cursor.getColumnIndexOrThrow(Contacts.CONTACTS_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contacts.CONTACTS_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contacts.CONTACT_EMAIL))
        );
        db.close();
        cursor.close();
        return contacts;
    }

    public ArrayList<Contacts> getallcontacts(){

        ArrayList<Contacts> contactlist = new ArrayList<Contacts>();

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + Contacts.TABLE_NAME + " ORDER BY "+ Contacts.CONTACTS_ID+ " DESC";

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Contacts contact = new Contacts();
                contact.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Contacts.CONTACTS_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.CONTACTS_NAME)));
                contact.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.CONTACT_EMAIL)));
                contactlist.add(contact);
            }while(cursor.moveToNext());
        }
        db.close();
        return contactlist;
    }

    public void updatecontact(Contacts contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contacts.CONTACTS_NAME ,contact.getName());
        values.put(Contacts.CONTACT_EMAIL,contact.getEmail());
        db.update(Contacts.TABLE_NAME,values,Contacts.CONTACTS_ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public void deletecontact(Contacts contacts){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Contacts.TABLE_NAME,Contacts.CONTACTS_ID +"=?",new String[]{String.valueOf(contacts.getId())});
        db.close();
    }
}
*/