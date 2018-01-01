package com.client.sportika;

import android.app.job.JobScheduler;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    JobScheduler mJobScheduler;

    AHBottomNavigation bottomNavigation;

    AHBottomNavigationItem item1,item2,item3,item4;
    private final String[] PAGE_TITLES = new String[] {
            "Purchase",
            "Sale",
            "Business Status"
    };

    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[] {
            new Page1Fragment(),
            new Page2Fragment(),
            new Page3Fragment()
    };

    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager mViewPager;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

         bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


//         item1 = new AHBottomNavigationItem(R.string.party, R.drawable.addparty, R.color.sweet_dialog_bg_color);
         item2 = new AHBottomNavigationItem(R.string.purchase, R.drawable.purchase, R.color.sweet_dialog_bg_color);
         item3 = new AHBottomNavigationItem(R.string.sale, R.drawable.sales, R.color.sweet_dialog_bg_color);
        item4 = new AHBottomNavigationItem(R.string.item, R.drawable.itemlist, R.color.sweet_dialog_bg_color);


// Add items
  //      bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
       bottomNavigation.addItem(item4);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));

        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
     //   bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#e74c3c"));
        bottomNavigation.setInactiveColor(Color.parseColor("#e74c3c"));

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);



        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...

               /* if(position == 0){

                    Intent intent = new Intent(MainActivity.this,AddParty.class);

                    startActivity(intent);

                }
*/
                if(position == 0){


                    Intent intent = new Intent(MainActivity.this,Purchase.class);

                    startActivity(intent);


                }

                if(position == 1){


                    Intent intent = new Intent(MainActivity.this,Sale.class);

                    startActivity(intent);


                }


                return true;
            }
        });




        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(mViewPager);

        // Titles of the individual pages (displayed in tabs)


    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }

}
