package com.example.kirill.yatl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DBHelper extends SQLiteOpenHelper {

    public String tableName = "task";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table " + tableName + " ("
                + "id integer primary key autoincrement,"
                + "taskName text,"
                + "done integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}
