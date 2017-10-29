package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by jimburk on 10/26/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IMAGE_URI = "image_uri";

    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String createTable = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_NAME + " TEXT, " + FIELD_DETAILS + " TEXT, " + FIELD_PHONE + " TEXT, " +
                FIELD_IMAGE_URI + " TEXT )";
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, GETALL

    public long addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, pet.getName());
        values.put(FIELD_DETAILS, pet.getDetails());
        values.put(FIELD_PHONE, pet.getPhone());
        values.put(FIELD_IMAGE_URI, pet.getImageUri().toString());

        long id = db.insert(DATABASE_TABLE, null, values);
        db.close();

        return id;
    }

    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(DATABASE_TABLE, new String[] {KEY_FIELD_ID, FIELD_NAME, FIELD_DETAILS,
                FIELD_PHONE, FIELD_IMAGE_URI}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        Uri.parse(cursor.getString(4)));
                petList.add(pet);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return petList;
    }

    public void deleteAllPets() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE, null, null);
        database.close();
    }
}
