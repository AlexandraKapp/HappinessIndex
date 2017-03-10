package com.kapp.happinessindex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kapp.happinessindex.data.Vote;
import com.kapp.happinessindex.utilities.NetworkUtils;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kapp.happinessindex.utilities.NetworkUtils.POST;

public class MainVoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    public static String SELECTED_HASHCODE_KEY = "hashcode key";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.drop_down_menu)
    Spinner mDropDownMenu;


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.vote_radio_button_group)
    RadioGroup mRadioGroup;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.green_button)
    Button greenButton;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.orange_button)
    Button orangeButton;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.red_button)
    Button redButton;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.version_footer)
    TextView version;

    String selectedHashTag;
    final int GREEN_SELECTED = 1;
    final int ORANGE_SELECTED = 2;
    final int RED_SELECTED = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vote);

        ButterKnife.bind(this);

        version.setText(getResources().getString(R.string.version_name, BuildConfig.VERSION_NAME));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hashcode_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDropDownMenu.setAdapter(adapter);
        mDropDownMenu.setOnItemSelectedListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);


    }

    public void vote(View view) {

        //TODO: (check if already voted)

        if (getSelectedValue() == -1) {
            Toast.makeText(this, "Choose green, orange or red before the vote can be submitted.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!isConnected()){
            Toast.makeText(this, "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
            return;
        }

        //TODO: set right Team name
        new HttpAsyncTask().execute(new Vote(selectedHashTag, "teamName", getSelectedValue(), System.currentTimeMillis()));

        Intent nextActivity = new Intent(MainVoteActivity.this, StatsActivity.class);
        nextActivity.putExtra(SELECTED_HASHCODE_KEY, selectedHashTag);

        startActivity(nextActivity);
    }

    /*
    returns the selected value green, orange or red
    if no value is selected -1 is returned
     */
    private int getSelectedValue() {

        int selectedValueButton = mRadioGroup.getCheckedRadioButtonId();

        if (selectedValueButton == greenButton.getId()) {
            return GREEN_SELECTED;
        } else if (selectedValueButton == orangeButton.getId()) {
            return ORANGE_SELECTED;
        } else if (selectedValueButton == redButton.getId()) {
            return RED_SELECTED;
        } else {
            return -1;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedHashTag = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int selectedValue = getSelectedValue();

        switch (selectedValue) {
            case GREEN_SELECTED:
                greenButton.setBackground(getDrawable(R.drawable.gradient_green));
                orangeButton.setBackground(getDrawable(R.color.basic_orange));
                redButton.setBackground(getDrawable(R.color.basic_red));

                greenButton.setElevation(getResources().getDimension(R.dimen.small_elevation));
                orangeButton.setElevation(getResources().getDimension(R.dimen.no_elevation));
                redButton.setElevation(getResources().getDimension(R.dimen.no_elevation));

                break;
            case ORANGE_SELECTED:
                greenButton.setBackground(getDrawable(R.color.basic_green));
                orangeButton.setBackground(getDrawable(R.drawable.gradient_orange));
                redButton.setBackground(getDrawable(R.color.basic_red));

                greenButton.setElevation(getResources().getDimension(R.dimen.no_elevation));
                orangeButton.setElevation(getResources().getDimension(R.dimen.small_elevation));
                redButton.setElevation(getResources().getDimension(R.dimen.no_elevation));

                break;
            case RED_SELECTED:
                greenButton.setBackground(getDrawable(R.color.basic_green));
                orangeButton.setBackground(getDrawable(R.color.basic_orange));
                redButton.setBackground(getDrawable(R.drawable.gradient_red));


                greenButton.setElevation(getResources().getDimension(R.dimen.no_elevation));
                orangeButton.setElevation(getResources().getDimension(R.dimen.no_elevation));
                redButton.setElevation(getResources().getDimension(R.dimen.small_elevation));
                break;
            default:
                greenButton.setBackground(getDrawable(R.color.basic_green));
                orangeButton.setBackground(getDrawable(R.color.basic_orange));
                redButton.setBackground(getDrawable(R.color.basic_red));
        }

    }

    private class HttpAsyncTask extends AsyncTask<Vote, Void, String> {
        @Override
        protected String doInBackground(Vote... votes) {
            return POST(NetworkUtils.HAPPINESS_INDEX_POST_SERVER, votes[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Response from Server: " + result, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


}