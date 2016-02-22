package creativestudioaq.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BackPressCloseHandler backpress;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Intent intent1 = new Intent(MainActivity.this, SplashActivity.class);
       startActivity(intent1);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);


        backpress = new BackPressCloseHandler(this);


    }

    public ViewPager getViewPager() {
        if (null == mViewPager) {
            mViewPager = (ViewPager) findViewById(R.id.container);
        }
        return mViewPager;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0)
                return new SettingFragment();
            else if (position == 1)
                return new MainFragment();
            else
                return new AlarmFragment();

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 2) {
            mViewPager.setCurrentItem(1);
        } else {
            backpress.onBackPressed();
        }
    }

}
