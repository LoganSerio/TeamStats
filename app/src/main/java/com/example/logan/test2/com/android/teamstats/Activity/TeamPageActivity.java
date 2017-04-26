package com.example.logan.test2.com.android.teamstats.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Fragment.TeamOptions;
import com.example.logan.test2.com.android.teamstats.Fragment.TeamOverview;

/**
 * A class that allows support for the team page.
 */
public class TeamPageActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Initializes the menu for the team page.
     * @param menu The menu in which items are placed.
     * @return returns true if the menu is displayed or false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * A method that calls when a menu item has been selected.
     * @item The MenuItem selected.
     * @return returns false to continue browsing the menu or true in the event an option was chosen.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A class that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * A method that gets the fragment item.
         * @param position the position of the item
         * @return a Fragment object
         */
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                TeamOverview tover = new TeamOverview();
                return tover;
            }
            else if(position == 1) {
                TeamOptions top = new TeamOptions();
                return top;
            }
            return null;
        }

        /**
         * Gets the page size.
         * @return Two total pages.
         */
        @Override
        public int getCount() {
            return 2;
        }

        /**
         * A method that gets the title of the page as either overview or options.
         * @param position The position of the desired page title.
         * @return returns a CharSequence of either "Team Overview" or "Team Options".
         */
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) {
                return "Team Overview";
            }
            else if (position == 1) {
                return "Team Options";
            }
            return null;
        }
    }
}
