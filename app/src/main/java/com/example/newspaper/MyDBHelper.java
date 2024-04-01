package com.example.newspaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newspaper.Models.NewsPapers;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "Mydatabase.db";
    private static String TABLE_NAME = "newsPapers";
    private static String KEY_ID = "id";
    private static String KEY_NAME = "name";
    private static String KEY_LINK = "link";
    private static String KEY_IMAGE = "image";
    private static int DB_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_NAME+" TEXT, "+KEY_LINK+" TEXT, "+KEY_IMAGE+" INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public void insertData(NewsPapers paper){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,paper.getName());
        cv.put(KEY_LINK,paper.getLink());
        cv.put(KEY_IMAGE,0);

        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<NewsPapers> fetchData(){
        ArrayList<NewsPapers>list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(1);
                String link = cursor.getString(2);
                int image = cursor.getInt(3);
                list.add(new NewsPapers(image,name,link));
            }while(cursor.moveToNext());
        }
        return list;
    }

    public void deleteDB(NewsPapers a){
        SQLiteDatabase db= this.getReadableDatabase();
        db.delete(TABLE_NAME,KEY_NAME+ "=?", new String[]{a.getName()});
    }
}
