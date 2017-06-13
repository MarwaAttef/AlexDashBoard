package com.example.wedad.design.welcomscreens;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.beanspkg.Tips;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwa on 6/3/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "alex1";
    public static final String TABLE_NAME = "FavouriteTable";
    public static final String Table_Column_ID = "id";
    public static final String Table_Column_1_Name = "name";
    public static final String Table_Column_10_Category = "category";
    public static final String Table_Column_2_PhoneNumber = "phone_number";
    public static final String Table_Column_3_Mobile = "mobile_number";
    public static final String Table_Column_4_Rate = "rate";
    public static final String Table_Column_5_Address = "address";
    public static final String Table_Column_6_Longtitude = "long_number";
    public static final String Table_Column_7_Lattitude = "lattiude_number";
    public static final String Table_Column_8_Icon = "icon";
    public static final String Table_Column_9_Review = "review";
    public static final String Table_Column_11_Date = "date";

    //--------------------------------- History Table ---------------------------------//
    public static final String History_TABLE_NAME = "HistoryTable";
    private static final int DATABASE_VERSION = 1;


    Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + Table_Column_1_Name + " VARCHAR, "
                + Table_Column_2_PhoneNumber + " VARCHAR, " + Table_Column_3_Mobile + " VARCHAR, "
                + Table_Column_4_Rate + " VARCHAR, " + Table_Column_5_Address + " VARCHAR, "
                + Table_Column_6_Longtitude + " REAL, " + Table_Column_7_Lattitude + " REAL, "
                + Table_Column_8_Icon + " VARCHAR, " + Table_Column_9_Review + " VARCHAR, "
                + Table_Column_10_Category + " VARCHAR, "+Table_Column_11_Date+"  VARCHAR )";
        database.execSQL(CREATE_TABLE);

        String History_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + History_TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + Table_Column_1_Name + " VARCHAR, "
                + Table_Column_2_PhoneNumber + " VARCHAR, " + Table_Column_3_Mobile + " VARCHAR, "
                + Table_Column_4_Rate + " VARCHAR, " + Table_Column_5_Address + " VARCHAR, "
                + Table_Column_6_Longtitude + " REAL, " + Table_Column_7_Lattitude + " REAL, "
                + Table_Column_8_Icon + " VARCHAR, " + Table_Column_9_Review + " VARCHAR,"
                + Table_Column_10_Category + " VARCHAR, "+Table_Column_11_Date+"  VARCHAR )";
        database.execSQL(History_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + History_TABLE_NAME);
        onCreate(db);
    }
    //------------------------------------insert into history table------------------------------------------//
    public void insertIntoHistoryDB(Place place) {
        Log.d("insert", "before insert");
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(Table_Column_1_Name, place.getName());
        values.put(Table_Column_2_PhoneNumber, place.getPhone());
        values.put(Table_Column_3_Mobile, place.getMobileUrl());
        values.put(Table_Column_4_Rate, place.getRating());
        values.put(Table_Column_5_Address, place.getAddress());
        values.put(Table_Column_6_Longtitude, place.getLog());
        values.put(Table_Column_7_Lattitude, place.getLat());
        values.put(Table_Column_8_Icon, place.getIcon());
        try {
            values.put(Table_Column_9_Review, place.getTip().getUrl());
        }
        catch(Exception e){
            Log.i("d","no url");
        }
        values.put(Table_Column_10_Category, place.getPopularName());
        values.put(Table_Column_11_Date, place.getDate());

        // 3. insert
        db.insert(History_TABLE_NAME, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG);
        Log.i("insert into DB", "After insert");
    }
    //------------------------------------insert into favourit table------------------------------------------//
    public void insertIntoFavouritDB(Place place) {
        Log.d("insert", "before insert");
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(Table_Column_1_Name, place.getName());
        values.put(Table_Column_2_PhoneNumber, place.getPhone());
        values.put(Table_Column_3_Mobile, place.getMobileUrl());
        values.put(Table_Column_4_Rate, place.getRating());
        values.put(Table_Column_5_Address, place.getAddress());
        values.put(Table_Column_6_Longtitude, place.getLog());
        values.put(Table_Column_7_Lattitude, place.getLat());
        values.put(Table_Column_8_Icon, place.getIcon());
        try {
            values.put(Table_Column_9_Review, place.getTip().getUrl());
        }catch (NullPointerException e){
            System.out.print("null review");

        }
        values.put(Table_Column_10_Category, place.getPopularName());
        values.put(Table_Column_11_Date, place.getDate());


        // 3. insert
        db.insert(TABLE_NAME, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG);
        Log.i("insert into DB", "After insert");
    }

//--------------------------------------get from history table -----------------------------------------//
        /* Retrive  data from database */
public List<Place> getDataFromHistoryDB() {
    List<Place> modelList = new ArrayList<Place>();
    String query = "select * from " + History_TABLE_NAME;
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
        do {
            Place place = new Place();
            place.setName(cursor.getString(1));
            place.setPhone(cursor.getString(2));
            place.setMobileUrl(cursor.getString(3));
            place.setRating(cursor.getString(4));
            place.setAddress(cursor.getString(5));
            place.setLog(cursor.getDouble(6));
            place.setLat(cursor.getDouble(7));
            place.setIcon(cursor.getString(8));
            Tips tip =new Tips();
            tip.setUrl(cursor.getString(9));
            place.setTip(tip);
            place.setPopularName(cursor.getString(10));
            place.setDate(cursor.getString(11));

            modelList.add(place);
        } while (cursor.moveToNext());
    }
    db.close();
    Log.d("place data", modelList.toString());
    return modelList;
}
    //--------------------------------------get from favourit table -----------------------------------------//
        /* Retrive  data from database */
    public List<Place> getDataFromFavouritDB() {
        List<Place> modelList = new ArrayList<Place>();
        String query = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setName(cursor.getString(1));
                place.setPhone(cursor.getString(2));
                place.setMobileUrl(cursor.getString(3));
                place.setRating(cursor.getString(4));
                place.setAddress(cursor.getString(5));
                place.setLog(cursor.getDouble(6));
                place.setLat(cursor.getDouble(7));
                place.setIcon(cursor.getString(8));
                Tips tip =new Tips();
                tip.setUrl(cursor.getString(9));
                place.setTip(tip);
                place.setPopularName(cursor.getString(10));
                place.setDate(cursor.getString(11));
                modelList.add(place);
            } while (cursor.moveToNext());
        }
        db.close();
        Log.d("place data", modelList.toString());
        return modelList;
    }
//------------------------------------------Delete row History-------------------------------------------//
    public void deleteARowHistory(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(History_TABLE_NAME,Table_Column_11_Date    + "    = ?", new String[] { date});
        db.close();
    }
//------------------------------------------Delete row Favourit-------------------------------------------//
public void deleteARowFavourit(String name){
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, Table_Column_1_Name    + "    = ?", new String[] { name});
    db.close();
}
    //-------------------------------------check exictance place in favourit table---------------------------------//


    //---------------------------- check repeating names--------------------------------------//
    public Boolean checkPlaceExistence(String name) {
        Boolean check=true;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

//editabale
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where " + Table_Column_1_Name + "=" + "\"" + name + "\";";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                check = false;
            }

        cursor.close();
            db.close();
        }
        catch (Exception e){
            Log.i("dataaaabase",e.toString());
        }
        return check;
    }
    //---------------------------- check repeating names in favourite--------------------------------------//

    public Boolean checkPlaceExistenceHistory(String name) {
        Boolean check=true;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

//editabale
            String selectQuery = "SELECT  * FROM " + History_TABLE_NAME + " where " + Table_Column_1_Name + "=" + "\"" + name + "\";";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                check = false;
            }

            cursor.close();
            db.close();
        }
        catch (Exception e){
            Log.i("dataaaabase",e.toString());
        }
        return check;
    }




}

