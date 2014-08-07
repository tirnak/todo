package com.example.kirill.yatl;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTask extends ActionBarActivity {

    TaskHandler taskHandler = new TaskHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {

        super.onResume();

        ((EditText) findViewById(R.id.taskNameEditText))
                .setText(
                    getIntent().getExtras().getString("taskName"),
                    TextView.BufferType.EDITABLE
                );

    }

    public void editTask(View view) {

        EditText taskNameWidget = (EditText) findViewById(R.id.taskNameEditText);

        String newTaskName = taskNameWidget.getText().toString();

        taskHandler.editTask(
            getIntent().getExtras().getInt("taskID"),
            newTaskName
        );

        this.finish();
    }

}
