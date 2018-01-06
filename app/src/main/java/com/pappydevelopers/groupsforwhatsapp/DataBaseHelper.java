package com.pappydevelopers.groupsforwhatsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yash on 13/7/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "SalaryCaluclation.db";
    public static final String TABLE_NAME = "group_table";
    public static final String COL_1 = "sno";
    public static final String COL_2 = "gp_name";
    public static final String COL_3 = "gp_category";
    public static final String COL_4 = "gp_link";
    public static final String COL_5 = "gp_image";
    public static final String TABLE_NAME1 = "reward_points_table";
    public static final String COL_6 = "reward_points";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS group_table(sno INTEGER PRIMARY KEY AUTOINCREMENT, gp_name BLOB(500), gp_category BLOB(100), gp_link BLOB(100), gp_image BLOB(100))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS reward_points_table(reward_points INTEGER)");
      }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertGroup(String sno,String gp_name, String gp_category,String gp_link,String gp_image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, sno);
        contentValues.put(COL_2, gp_name);
        contentValues.put(COL_3, gp_category);
        contentValues.put(COL_4, gp_link);
        contentValues.put(COL_5, gp_image);
        db.insert(TABLE_NAME, null, contentValues);
    }


    public void insertReward(Integer rewards) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, rewards);
        db.insert(TABLE_NAME1, null, contentValues);
    }

    public Cursor getReward(){
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        return c;
    }

    public Cursor getGroups(){
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

    public Cursor getStoryByCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr1 = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where gp_category = '" + category + "'", null);
        return cr1;
    }


}
