package tech.digitalcraft.daddysburger.Controller.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "daddys.db";
    public static final String TABLE_1 = "items_table";
    public static final String TABLE_1_COL_1 = "ID";
    public static final String TABLE_1_COL_2 = "NAME";
    public static final String TABLE_1_COL_3 = "IMG";
    public static final String TABLE_1_COL_4 = "PRICE";
    public static final String TABLE_1_COL_5 = "DESCRIPTION";
    public static final String TABLE_1_COL_6 = "CATEGORY_ID";
    public static final String TABLE_1_COL_7 = "QTY";






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ TABLE_1 +" ( ID INTEGER ,  NAME TEXT , IMG INTEGER , PRICE FLOAT , DESCRIPTION TEXT , CATEGORY_ID INTEGER , QTY INTEGER)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+ TABLE_1);

        onCreate(db);
    }



    public boolean insertItems(int id,int img , String name , String desc , float price , int category , int qty)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL_1,id);
        contentValues.put(TABLE_1_COL_2,name);
        contentValues.put(TABLE_1_COL_3,img);
        contentValues.put(TABLE_1_COL_4,price);
        contentValues.put(TABLE_1_COL_5,desc);
        contentValues.put(TABLE_1_COL_6,category);
        contentValues.put(TABLE_1_COL_7,qty);



        float result = db.insert(TABLE_1, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }



    public Cursor getItems()
    {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor res = db.rawQuery("select * from " + TABLE_1 , null);

        return res;

    }



    public boolean deleteItem(int item_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();


       int result =  db.delete(TABLE_1 , "ID = ?" , new String[]{String.valueOf(item_id)});

        if (result > 0)
            return true;
        else
            return false;
    }




    public Cursor checkWish(int user_id , int item_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM user_wish_table WHERE USER_ID = ? and ITEM_ID = ?";

        Cursor res = db.rawQuery(query, new String[]{String.valueOf(user_id) , String.valueOf(item_id)});


        return res;

    }



    public boolean checkout()
    {
        SQLiteDatabase db = this.getWritableDatabase();


        int result =  db.delete(TABLE_1 , null , null);

        if (result > 0)
            return true;
        else
            return false;
    }

}
