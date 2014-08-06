package com.example.kirill.yatl;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;


public class main extends FragmentActivity {

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
        viewPager.setCurrentItem(0);

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

        LinearLayout activeTaskWrapperLayout = (LinearLayout) pagerAdapter.findViewById(
                0,
                R.id.taskWrapperLayout
        );

        LinearLayout doneTaskWrapperLayout = (LinearLayout) pagerAdapter.findViewById(
                1,
                R.id.taskWrapperLayout
        );

        taskHandler.refreshTasks();

    }

    public void addTask(View view) {

        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);

    }



}
