package com.example.kirill.yatl;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * Created by kirill on 8/4/14.
 */
public class TaskHandler {

    private Activity callingClassScope;

    public TaskHandler(Activity newCallingClassScope) {
        callingClassScope = newCallingClassScope;
    }

    public void refreshTasks() {

        LinearLayout activeTaskWrapperLayout = (LinearLayout) ((Main) callingClassScope).pagerAdapter.findViewById(
                0,
                R.id.taskWrapperLayout
        );

        LinearLayout doneTaskWrapperLayout = (LinearLayout) ((Main) callingClassScope).pagerAdapter.findViewById(
                1,
                R.id.taskWrapperLayout
        );

        activeTaskWrapperLayout.removeAllViews();
        doneTaskWrapperLayout.removeAllViews();

        DBHelper dbHelper = new DBHelper(callingClassScope);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(dbHelper.tableName, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("taskName");
            int doneColIndex = c.getColumnIndex("done");

            do {

                LinearLayout layoutToInsert = null;

                if (c.getInt(doneColIndex) == 0) {
                    layoutToInsert = activeTaskWrapperLayout;
                } else {
                    layoutToInsert = doneTaskWrapperLayout;
                }

                TaskCheckBox task = new TaskCheckBox(callingClassScope);

                final TaskHandler taskHandler = this;

                task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton task, boolean isChecked) {
                        taskHandler.switchState((TaskCheckBox) task);

                        taskHandler.refreshTasks();
                    }
                });

                task.setText(c.getString(nameColIndex));
                task.setTaskID(c.getInt(idColIndex));
                task.setId(((Main) callingClassScope).generateViewId());

                callingClassScope.registerForContextMenu(task);

                layoutToInsert.addView(task);

                // получаем значения по номерам столбцов и пишем все в лог
                System.out.println("ID = " + c.getInt(idColIndex) +
                        ", name = " + c.getString(nameColIndex) +
                        ", done = " + c.getInt(doneColIndex));
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

        System.out.println("--- Insert in " + dbHelper.tableName + ": ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        cv.put("taskName", task.name);
        cv.put("done", 0);

        // вставляем запись и получаем ее ID
        long rowID = db.insert(dbHelper.tableName, null, cv);
        System.out.println("row inserted, ID = " + rowID);

        dbHelper.close();

    }

    public void editTask(int taskID, String newName) {

        int id = taskID;
        System.out.println("id is " + id);

        //create db wrapper
        DBHelper dbHelper = new DBHelper(callingClassScope);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //update task: set done=1

        db.execSQL(
                " UPDATE " +
                        dbHelper.tableName +
                        " SET taskName = " +
                        "\"" + newName + "\" " +
                        "WHERE " +
                        "id = " +
                        id
        );

        dbHelper.close();

    }

    public void switchState(TaskCheckBox checkBox) {

        //remove view
        ((ViewManager) checkBox.getParent()).removeView(checkBox);

        int id = checkBox.getTaskID();
        System.out.println("id is " + id);

        //create db wrapper
        DBHelper dbHelper = new DBHelper(callingClassScope);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //update task: set done=1

        db.execSQL(
                " UPDATE " +
                        dbHelper.tableName +
                        " SET done = " +
                        "(CASE " +
                        "WHEN " +
                        "(done = 0) " +
                        "THEN " +
                        "1 " +
                        "ELSE " +
                        "0 " +
                        "END) " +
                        "WHERE " +
                        "id = " +
                        id
        );

        dbHelper.close();
    }

}
