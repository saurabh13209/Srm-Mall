package com.saurabh.srmmall;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;

import java.util.ArrayList;

public class ShareHolder extends DatabaseHandler {


    SharedPreferences sharedPreferences;
    Context context;
    DatabaseHandler databaseHandler;

    public ShareHolder(Context context) {
        super(context);
        sharedPreferences = context.getSharedPreferences("Database", Context.MODE_PRIVATE);
        this.context = context;
        databaseHandler = new DatabaseHandler(context);

    }

    String ShopName() {
        return sharedPreferences.getString("ShopName", "");
    }

    String GetMob(){
        return sharedPreferences.getString("Mob","");
    }

    void SetShopKeeper(String shopName, String floor, String hostel , String Mob) {
        Editor editor = sharedPreferences.edit();
        editor.putString("ShopName", shopName);
        editor.putString("floor", floor);
        editor.putString("hostel", hostel);
        editor.putString("Mob" , Mob);
        editor.commit();
    }


    void SetName(String Id) {
        Editor editor = sharedPreferences.edit();
        editor.putString("Name", Id);
        editor.commit();
    }



    void SetId(String Id) {
        Editor editor = sharedPreferences.edit();
        editor.putString("Id", Id);
        editor.commit();
    }

    void SetAndroidId(String Id) {
        Editor editor = sharedPreferences.edit();
        editor.putString("Basic", Id);
        editor.commit();
    }

    String getName() {
        return sharedPreferences.getString("Name", "");
    }

    String getHostel() {
        return sharedPreferences.getString("hostel", "");
    }

    String getFloor() {
        return sharedPreferences.getString("floor", "");
    }

    String getId() {
        return sharedPreferences.getString("Id", "");
    }

    String getAndroidId() {
        return sharedPreferences.getString("Basic", "");
    }

    public ArrayList getMessageList() {
        ArrayList Main = new ArrayList<>();
        Cursor MessageList = databaseHandler.MessageList();
        while (MessageList.moveToNext()) {
            ArrayList temp = new ArrayList();
            temp.add(MessageList.getString(0).toString());
            temp.add(MessageList.getString(1).toString());
            Main.add(temp);
        }
        return Main;
    }

}
