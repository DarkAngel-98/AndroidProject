package com.example.remainder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db" ;
    private static final int VERSION = 1 ;
    private static final String TABLE_NAME = "my_remainders" ;
    private static final String COLUMN_ID = "id" ;
    private static final String DATE = "calendarDate" ;
    private static final String TIME = "calendarTime" ;
    private static final String CATEGORY = "category" ;
    private static final String REMAINDER = "remainder" ;

    public static final String TAG = "Database" ;

    String date, time, category ;
    SimpleDateFormat simpleDateFormat ;


    private Context context ;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context ;
    }

    // onCreate is called when we access the database for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT, "
                + TIME + " TEXT, "
                + REMAINDER + " TEXT, "
                + CATEGORY + " TEXT); ";

        db.execSQL(query);
    }

    // onUpgrade is called when we change something in our database, like adding a column.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addOne(String date, String time, String remainder, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DATE, date) ;
        cv.put(TIME, time) ;
        cv.put(REMAINDER, remainder) ;
        cv.put(CATEGORY, category) ;
        long result = db.insert(TABLE_NAME, null, cv) ;
        if(result == -1)
            return false ;
        return true ;
    }

    public String getCurrentDate(){
        String currentDate = null ;
        Calendar calendar = Calendar.getInstance() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMd");
        currentDate = simpleDateFormat.format(calendar.getTime()) ;
        return currentDate ;
    }

    String todayDate = getCurrentDate() ;

    public List<String> returnData(){
        Log.i("TodayDate", todayDate) ;
        List<String> returnList = new ArrayList<String>() ;
        String returnRow ;

        String queueString = "SELECT * FROM " + TABLE_NAME + " WHERE " + DATE +   " = " + todayDate ;
        // + " WHERE date('" + DATE + "')" + " = " + "date('" + "2021-6-26" + "')"
        // + " WHERE " + DATE + " = " + "2021-6-26"
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(queueString, null) ;

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0) ;
                String date = cursor.getString(1) ;
                String time = cursor.getString(2) ;
                String remainder = cursor.getString(3) ;
                String category = cursor.getString(4) ;
                
                returnRow = "At " + time + " : " + remainder ;
                returnList.add(returnRow) ;

            }while (cursor.moveToNext());

        }
        else {
        }
        cursor.close();
        db.close();
        return returnList ;
    }

    public boolean checkIfTodaysDateExists(){
        Cursor cursor ;
        String query ;
        SQLiteDatabase db = this.getReadableDatabase() ;
        query = "SELECT " + DATE + " FROM " + TABLE_NAME + " WHERE " + DATE + " = " + todayDate ;
        cursor = db.rawQuery(query, null) ;
        cursor.moveToFirst() ;
        if(cursor.getCount() > 0) {
            //Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            //Toast.makeText(context, "NO", Toast.LENGTH_SHORT).show();
            return false ;
        }
    }



}
