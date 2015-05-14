package com.example.jiayi.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayi on 5/13/15.
 */
public class FavoriteDB extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "main";

    // Table Name
    private static final String TABLE = "favorite";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DEPART = "depart";
    private static final String KEY_DESTINATION = "destination";


    public FavoriteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for favorite items
        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DEPART + " TEXT,"
                + KEY_DESTINATION + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            // Create tables again
            onCreate(db);
        }
    }

    // Insert record into the database
    public void addFavorite(FavoriteItem item) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();
        values.put(KEY_DEPART, item.getDepart());
        values.put(KEY_DESTINATION, item.getDestination());
        // Insert Row
        db.insertOrThrow(TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Returns a single favorite item by id
    public FavoriteItem getFavoriteItem(int id) {
        // Open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query(TABLE,  // TABLE
                new String[]{KEY_ID, KEY_DEPART, KEY_DESTINATION}, // SELECT
                KEY_ID + "= ?", new String[]{String.valueOf(id)},  // WHERE, ARGS
                null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        FavoriteItem item = new FavoriteItem(cursor.getString(1), cursor.getString(2));
        // Close the cursor
        if (cursor != null)
            cursor.close();
        // return favorite item
        return item;
    }

    public List<FavoriteItem> getAllFavoriteItems() {
        List<FavoriteItem> favoriteItems = new ArrayList<FavoriteItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FavoriteItem item = new FavoriteItem(cursor.getString(1), cursor.getString(2));
                item.setId(cursor.getInt(0));
                // Adding todo item to list
                favoriteItems.add(item);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        if (cursor != null)
            cursor.close();

        // return favorite list
        return favoriteItems;
    }


}