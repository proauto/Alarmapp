package creativestudioaq.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BackPressCloseHandler backpress;
    private ViewPager mViewPager;
    MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                int i=position;
                if(i==0){
                    if(MainFragment.button1!=null)
                        MainFragment.button1.setVisibility(View.INVISIBLE);
                    if(MainFragment.button2!=null)
                        MainFragment.button2.setVisibility(View.INVISIBLE);
                    if(MainFragment.button3!=null)
                        MainFragment.button3.setVisibility(View.INVISIBLE);
                }else if(i==1){
                    if(MainFragment.button1!=null)
                        MainFragment.button1.setVisibility(View.VISIBLE);
                    if(MainFragment.button2!=null)
                        MainFragment.button2.setVisibility(View.VISIBLE);
                    if(MainFragment.button3!=null)
                        MainFragment.button3.setVisibility(View.VISIBLE);
                }else{
                    if(MainFragment.button1!=null)
                        MainFragment.button1.setVisibility(View.INVISIBLE);
                    if(MainFragment.button2!=null)
                        MainFragment.button2.setVisibility(View.INVISIBLE);
                    if(MainFragment.button3!=null)
                        MainFragment.button3.setVisibility(View.INVISIBLE);
                }
            }
        });

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

            if (position == 0) {


                return new SettingFragment();

            } else if (position == 1) {

                return new MainFragment();
            } else {

                return new AlarmFragment();
            }
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
