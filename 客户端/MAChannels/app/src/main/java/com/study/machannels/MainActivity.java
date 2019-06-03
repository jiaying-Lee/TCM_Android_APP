package com.study.machannels;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.study.machannels.Diagnosis.DiagnosisMainFragment;
import com.study.machannels.Home.HomeMainFragment;
import com.study.machannels.Search.SearchMainFragment;
import com.study.machannels.Own.OwnMainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        private int mCurrentPosition = 0;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    if(mCurrentPosition != 0){
                        mCurrentPosition =0;
                        mViewPager.setCurrentItem(mCurrentPosition);
                    }
                    return true;
                case R.id.navigation_diagnosis:
                    if(mCurrentPosition != 1) {
                        mCurrentPosition = 1;
                        mViewPager.setCurrentItem(mCurrentPosition);
                    }
                    return true;
                case R.id.navigation_search:
                    if(mCurrentPosition != 2) {
                        mCurrentPosition = 2;
                        mViewPager.setCurrentItem(mCurrentPosition);
                    }
                    return true;
                case R.id.navigation_own:
                    if(mCurrentPosition != 3) {
                        mCurrentPosition = 3;
                        mViewPager.setCurrentItem(mCurrentPosition);
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.vp_container);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bt_navigation);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mBottomNavigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        final ArrayList<Fragment> FragmentLists = new ArrayList<>(4);
        FragmentLists.add(new HomeMainFragment());
        FragmentLists.add(new DiagnosisMainFragment());
        FragmentLists.add(new SearchMainFragment());
        FragmentLists.add(new OwnMainFragment());

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),FragmentLists);

        mViewPager.setAdapter(mViewPagerAdapter);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
