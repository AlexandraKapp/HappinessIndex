package com.kapp.happinessindex;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.kapp.happinessindex.data.HashtagResult;

import static android.R.id.tabs;


public class TabFragmentAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Today", "Monthly Stats"};
    private Context context;

    TabFragment[] currentTabs = new TabFragment[PAGE_COUNT];

    public TabFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("DEBUG", "getItem called");
        currentTabs[position] = TabFragment.newInstance(position + 1);
        return currentTabs[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    public void setTabData(HashtagResult hashTag) {
        Log.d("DEBUG", "setTab Data called");
        int counter = 0;
        for (TabFragment tab : currentTabs) {
            counter++;
            Log.d("DEBUG", String.valueOf(counter));
            Log.d("DEBUG", "hashtag " + hashTag);
            Log.d("Debug", "tab " + tab);
            tab.setData(hashTag);
        }
    }

    public boolean isCurrentTabEmpty() {
        for (int i = 0; i < PAGE_COUNT; i++) {
            if (currentTabs[i] == null) {
                return true;
            }
        }
        return false;
    }

}
