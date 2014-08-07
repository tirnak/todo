package com.example.kirill.yatl;

import android.content.Context;
import android.widget.CheckBox;

/**
 * Created by kirill on 07.08.14.
 */
public class TaskCheckBox extends CheckBox {

    private int taskID;

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int id) {
        taskID = id;
    }

    public TaskCheckBox(Context context) {
        super(context);
    }
}
