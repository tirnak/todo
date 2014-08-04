package com.example.kirill.yatl;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kirill on 8/4/14.
 */
public class TaskHandler {

    private Activity callingClassScope;

    public TaskHandler(Activity newCallingClassScope) {
        callingClassScope = newCallingClassScope;
    }

    public void refreshTasks(LinearLayout taskWrapperLayout) {

        taskWrapperLayout.removeAllViews();

        DBHelper dbHelper = new DBHelper(callingClassScope);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("taskName");

            do {
                TextView task = new TextView(callingClassScope);
                task.setText(c.getString(nameColIndex));
                task.setContentDescription("task" + c.getInt(idColIndex));
                taskWrapperLayout.addView(task);

                // получаем значения по номерам столбцов и пишем все в лог
                System.out.println("ID = " + c.getInt(idColIndex) +
                        ", name = " + c.getString(nameColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            System.out.println("0 rows");
        c.close();

        dbHelper.close();
    }

    public void addNewTask(Task task) {
        ContentValues cv = new ContentValues();

        DBHelper dbHelper = new DBHelper(callingClassScope);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        System.out.println("--- Insert in mytable: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        cv.put("taskName", task.name);

        // вставляем запись и получаем ее ID
        long rowID = db.insert("mytable", null, cv);
        System.out.println("row inserted, ID = " + rowID);

        dbHelper.close();

    }

}
