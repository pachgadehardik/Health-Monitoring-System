package com.example.hemal.hms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.*;


/**
 * Created by hemal on 16/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "User.db";
    public static final String MAIN_TABLE = "UserTable";
    public static final String CID = "ID";
    public static final String CNAME = "FullName";
    public static final String CMAIL = "Email";
    public static final String CAGE = "Age";
    public static final String CSEX = "Sex";
    public static final String CWEIGHT = "Weight";
    public static final String CHEIGHT = "Height";
    public static final String CPWD = "Pwd";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + MAIN_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FULLNAME TEXT " +
                ", EMAIL TEXT " +
                ", AGE INTEGER " +
                ", SEX TEXT " +
                ", WEIGHT REAL " +
                ", HEIGHT REAL " +
                ", PWD TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + MAIN_TABLE);
    }

    public boolean insertData(Sign_up su, String n, String m, String p, String a, String s, String w, String h) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        /*String query="select * from UserTable";
        Cursor cursor=db.rawQuery(query,null);
        int count=cursor.getCount();
        cV.put(CID,count);*/
        {
            if (n.length() == 0) {
                Toast.makeText(su, "Full Name not entered", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (m.length() == 0) {
                Toast.makeText(su, "Mail not entered", Toast.LENGTH_SHORT).show();
                return false;

            }
            if (p.length() == 0) {
                Toast.makeText(su, "Password not entered", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (a.length() == 0) {
                Toast.makeText(su, "Age not entered", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (s.length() == 0) {
                Toast.makeText(su, "Sex not entered", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (w.length() == 0) {
                Toast.makeText(su, "Weight not entered", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (h.length() == 0) {
                Toast.makeText(su, "Height not entered", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        cV.put(CNAME, n);
        cV.put(CMAIL, m);
        cV.put(CPWD, p);
        cV.put(CAGE, a);
        cV.put(CSEX, s);
        cV.put(CWEIGHT, w);
        cV.put(CHEIGHT, h);
        long r = db.insert(MAIN_TABLE, null, cV);
        if (r == -1) return false;
        else return true;
    }

    public String searchPass(String EMAIL) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select Email,Pwd from " + MAIN_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        String a;
        String b;
        b = "Not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(EMAIL)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;


    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + MAIN_TABLE, null);
        return res;
    }

    public static final String c1 = "Day";
    public static final String c3 = "Burnt";
    public static final String c4 = "Total";
    public static final String c2 = "Consumed";

    boolean createUserTable(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table " + id +
                " (Day INTEGER PRIMARY KEY ,Consumed REAL " +
                ", Burnt REAL " +
                ", Total REAL )");
        ContentValues cV = new ContentValues();
        cV.put(c1, 1);
        cV.put(c2, 0);
        cV.put(c3, 0);
        cV.put(c4, 0);
        return true;
    }


    int getCaloriesBurnt(int id) {

        Integer i = new Integer(id);
        SQLiteDatabase db = this.getReadableDatabase();
        String str = "SELECT * FROM " + id + " WHERE DAY = 1";
        Cursor c = db.rawQuery(str, null);
        if (c != null)
            c.moveToFirst();
        return Integer.parseInt(c.getString(2));
    }


    int getCaloriesConsumed(int id) {
        Integer i = new Integer(id);
        SQLiteDatabase db = this.getReadableDatabase();
        String str = "SELECT * FROM " + id + " WHERE DAY = 1";
        Cursor c = db.rawQuery(str, null);
        if (c != null)
            c.moveToFirst();
        return Integer.parseInt(c.getString(1));
    }

    boolean setCalories(int id,float calb,float calc)
    {
        Integer i = new Integer(id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(c2,calc);
        cv.put(c3,calb);
        db.update(i.toString(),cv,"day = 1",null);
        //String str = "UPDATE "+ id +" SET "+ c3 +" = "+cal+" WHERE DAY = 1";
        //db.execSQL(str);
        return true;
    }

    float wtInPound() {
        //SQLiteDatabase db = new SQLiteDatabase(this);
        SQLiteDatabase db = this.getReadableDatabase();
        String str = "SELECT * FROM "+MAIN_TABLE+" WHERE ID=1";
        Cursor c = db.rawQuery(str, null);
        if (c != null)
            c.moveToFirst();
        float wt =  Float.parseFloat(c.getString(5));
        wt = wt * 2.204f;
        return wt;

    }
}