package com.kapp.happinessindex;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements TabFragment.OnFragmentInteractionListener {

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

        if (getIntent().getExtras() != null) {
            String hashCode = getIntent().getStringExtra(MainVoteActivity.SELECTED_HASHCODE_KEY);
            mHashCode.setText(hashCode);
        }

        mAtTeam.setText("@exampleTeam");

        mViewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), StatsActivity.this));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.make_new_vote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(StatsActivity.this, MainVoteActivity.class));
        return true;
    }
}
