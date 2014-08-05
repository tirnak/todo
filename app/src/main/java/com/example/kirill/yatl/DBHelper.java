package com.example.kirill.yatl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table task ("
                + "id integer primary key autoincrement,"
                + "taskName text,"
                + "done integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS task");
        db.execSQL("DROP TABLE IF EXISTS mytable");
        onCreate(db);
    }
}
