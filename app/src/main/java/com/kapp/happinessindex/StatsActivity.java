package com.kapp.happinessindex;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.kapp.happinessindex.data.Team;
import com.kapp.happinessindex.utilities.HappinessIndexUtils;
import com.kapp.happinessindex.utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

public class StatsActivity extends AppCompatActivity implements TabFragment.OnFragmentInteractionListener, LoaderManager.LoaderCallbacks<ArrayList<Team>> {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.hashtag_as_title)
    TextView mHashTagDisplay;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.team_name)
    TextView mAtTeam;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    final String HASHTAG_KEY = "hashtag key";

    //set up for Loader
    final LoaderManager.LoaderCallbacks<ArrayList<Team>> callbacks = StatsActivity.this;
    final int loaderId = 1;

    private Team mTeam;
    private String mHashTag;
    TabFragmentAdapter mTabFragmentAdapter;


    //TODO: keep data when orientation changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ButterKnife.bind(this);

        if (getIntent().getExtras() == null) {
            //TODO: better error handling
            return;
        }

        mHashTag = getIntent().getStringExtra(MainVoteActivity.SELECTED_HASHCODE_KEY);
        mHashTagDisplay.setText("#" + mHashTag);

        Bundle bundle = new Bundle();
        bundle.putString(HASHTAG_KEY, mHashTag);

        mTabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), StatsActivity.this);
        getSupportLoaderManager().initLoader(loaderId, bundle, callbacks);

        mViewPager.setAdapter(mTabFragmentAdapter);
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

    @Override
    public Loader<ArrayList<Team>> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<ArrayList<Team>>(this) {

            ArrayList<Team> teams;


            @Override
            protected void onStartLoading() {
                if (teams != null) {
                    deliverResult(teams);
                } else {
                    //loading Indicator: set Visible
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Team> loadInBackground() {

                //TODO: add real server request
                //URL happinessIndexRequestUrl = NetworkUtils.buildURL(args.getString(CURRENT_TEAM), args.getString(CURRENT_HASHTAG));
                //String jsonString = "{\"results\": [{\"team\": \"Team A\",\"tags\": [{\"hashTag\":\"#Code\",\"green\": 28,\"orange\": 21,\"red\": 45,\"total_votes\": 94,\"date\": 1488957464291}]}],\"total_team_amount\": 2}";
                try {
                    URL happinessIndexRequestUrl = new URL(NetworkUtils.HAPPINESS_INDEX_SERVER);
                    String jsonString = NetworkUtils.getResponseFromHttpUrl(happinessIndexRequestUrl);
                    teams = HappinessIndexUtils.getTeamObjectsFromJson(StatsActivity.this, jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return teams;
            }

            @Override
            public void deliverResult(ArrayList<Team> data) {
                teams = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Team>> loader, ArrayList<Team> data) {
        //Loading Indicatior set Invisible

        setNewResults(data);
        mAtTeam.setText("@" + mTeam.getTeamName());

        //else: error message
    }

    private void setNewResults(ArrayList<Team> data) {
        //either choose right Team here or adapt Json Result
        //e.g. check if only 1 instance in Array
        mTeam = data.get(0);

        if (!mTabFragmentAdapter.isCurrentTabEmpty()) {
            mTabFragmentAdapter.setTabData(mTeam.getHashTags().get(0));
            mViewPager.getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}
