package com.example.mysurveyapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;


import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "survey_db";
    public static final String TABLE_NAME = "survey_table";
    public static final String TABLE2_NAME = "track_table";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME+" (RESPONSE_NO INTEGER NOT NULL, QUES_NO INTEGER NOT NULL, QUESTION TEXT, MY_ANS TEXT, CONSTRAINT PK_1 PRIMARY KEY (RESPONSE_NO, QUES_NO))");
        sqLiteDatabase.execSQL("create table "+ TABLE2_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIMEVAL TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean insertSData(Integer respno, Integer quesno, String ques, String ans)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RESPONSE_NO", respno);
        contentValues.put("QUES_NO", quesno);
        contentValues.put("QUESTION", ques);
        contentValues.put("MY_ANS", ans);
        long res = db.insert(TABLE_NAME, null, contentValues);
        if(res==-1)
            return false;
        else
            return true;
    }

    public boolean insertTData(Timestamp timestamp)
    {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("TIMEVAL", String.valueOf(timestamp));
        long res = db1.insert(TABLE2_NAME, null, contentValues1);
        if(res==-1)
            return false;
        else
            return true;
    }
    public Cursor getTrack()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE2_NAME+" WHERE ID= (SELECT MAX(ID) FROM "+TABLE2_NAME+")", null);
        return cursor;
    }
    public Cursor getTrackAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE2_NAME, null);
        return cursor;
    }
    public Cursor getAllData(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE RESPONSE_NO="+id, null);
        return cursor;
    }
}
