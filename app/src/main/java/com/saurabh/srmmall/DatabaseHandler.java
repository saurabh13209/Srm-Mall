package com.saurabh.srmmall;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    final static int VERSION = 1;

    final static String MessageTableName = "MessageTable";
    final static String MessageFromName = "FromName";
    final static String MessageMain = "Message";

    public DatabaseHandler(Context context) {
        super(context, "Database", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Message = "create table "+MessageTableName+" ( "+
                MessageFromName+" text , " +
                MessageMain+" text ); ";
        sqLiteDatabase.execSQL(Message);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertMessageList(String Name , String Message){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageFromName ,Name);
        contentValues.put(MessageMain,Message);
        sqLiteDatabase.insert(MessageTableName , null , contentValues);
        sqLiteDatabase.close();
    }

    public Cursor MessageList() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select "+MessageFromName+" , "+MessageMain+" from "+MessageTableName;
        Cursor messageList =  sqLiteDatabase.rawQuery(sql,null);
        return messageList;
    }
}
