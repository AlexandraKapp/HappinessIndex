package com.kapp.happinessindex;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements TabFragment.OnFragmentInteractionListener{

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.hashtag_as_title)
    TextView mHashCode;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.team_name)
    TextView mAtTeam;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ButterKnife.bind(this);

        //TODO: pack and unpack bundle and set Hashcode, Teamname and stats

        mHashCode.setText("#HashCode");
        mAtTeam.setText("@exampleTeam");

        mViewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), StatsActivity.this));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
