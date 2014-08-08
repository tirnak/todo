package com.example.kirill.yatl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


public class Main extends FragmentActivity {

    public static final int ACTIVE_TASK_PAGE = 0;
    public static final int FINSHED_TASK_PAGE = 1;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    CustomPagerAdapter pagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;

    /**
     * Initiate task handler
     */
    TaskHandler taskHandler = new TaskHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.activity_main, null);
        pages.add(page);

        page = inflater.inflate(R.layout.activity_main, null);
        LinearLayout buttonWrapperLayout = (LinearLayout) page.findViewById(R.id.buttonWrapperLayout);
        buttonWrapperLayout.setVisibility(View.GONE);
        pages.add(page);

        pagerAdapter = new CustomPagerAdapter(pages);
        viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(ACTIVE_TASK_PAGE);

        setContentView(viewPager);

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        final Context tmpContext = this;
//
//        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                Auth auth = new Auth(tmpContext);
//
//                return "adsf" ;//auth.asdf();
//            }
//
//            @Override
//            protected void onPostExecute(String token) {
//                System.out.println("token1 is" + token);
//            }
//        };
//        task.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        taskHandler.refreshTasks();

    }

    public void addTask(View view) {

        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);

    }

    public void editTask(int taskID, String taskName) {

        Intent intent = new Intent(this, EditTask.class);
        intent.putExtra("taskID", taskID);
        intent.putExtra("taskName", taskName);
        startActivity(intent);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Действие над задачей");

        if (viewPager.getCurrentItem() == ACTIVE_TASK_PAGE) {
            menu.add(0, v.getId(), 0, getString(R.string.finish_string));
            menu.add(0, v.getId(), 0, getString(R.string.edit_string));
        }

        menu.add(0, v.getId(), 0, getString(R.string.delete_string));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        TaskCheckBox tcb = (TaskCheckBox) pagerAdapter.findViewById(
          viewPager.getCurrentItem(),
          item.getItemId()
        );

       if (item.getTitle() == getString(R.string.finish_string)) {

            taskHandler.switchState(tcb);
            taskHandler.refreshTasks();

       } else if (item.getTitle() == getString(R.string.edit_string)) {

            editTask(
                tcb.getTaskID(),
                (String) tcb.getText()
            );

       } else if (item.getTitle() == getString(R.string.delete_string)) {

            taskHandler.deleteTask(tcb);
            taskHandler.refreshTasks();

       }
       return true;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in {@link #setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
