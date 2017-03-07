package com.kapp.happinessindex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainVoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static String SELECTED_HASHCODE_KEY = "hashcode key";
    Spinner mDropDownMenu;
    String selectedHashCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vote);
        mDropDownMenu = (Spinner) findViewById(R.id.drop_down_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hashcode_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDropDownMenu.setAdapter(adapter);
        mDropDownMenu.setOnItemSelectedListener(this);


    }

    public void vote(View view) {

        //TODO: check if a button is selected
        //TODO: check if already voted
        //TODO: check if hashtag is selected
        //TODO: add vote to database

        Log.e("DEBUG", "selected Hash Code " + selectedHashCode);

        Intent nextActivity = new Intent(MainVoteActivity.this, StatsActivity.class);
        nextActivity.putExtra(SELECTED_HASHCODE_KEY, selectedHashCode);

        startActivity(nextActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("DEBUG", "get selected item in onItemselected " + (String) parent.getItemAtPosition(position));
        selectedHashCode = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
