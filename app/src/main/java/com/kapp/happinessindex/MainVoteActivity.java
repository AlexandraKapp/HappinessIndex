package com.kapp.happinessindex;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;
import static com.kapp.happinessindex.R.id.vote_radio_button_group;

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

    String selectedHashCode;
    final int GREEN_SELECTED = 1;
    final int ORANGE_SELECTED = 2;
    final int RED_SELECTED = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vote);

        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hashcode_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDropDownMenu.setAdapter(adapter);
        mDropDownMenu.setOnItemSelectedListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);


    }

    public void vote(View view) {

        //TODO: check if a button is selected
        //TODO: check if already voted
        //TODO: check if hashtag is selected

        //TODO: add vote to database

        //getSelectedValue();

        Intent nextActivity = new Intent(MainVoteActivity.this, StatsActivity.class);
        nextActivity.putExtra(SELECTED_HASHCODE_KEY, selectedHashCode);

        startActivity(nextActivity);
    }

    /*
    returns the selected value green, orange or red
    if no value is selected -1 is returned
     */
    private int getSelectedValue() {

        //TODO: fix bug nullpointerexception

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
        selectedHashCode = (String) parent.getItemAtPosition(position);
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
}
