package com.kapp.happinessindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainVoteActivity extends AppCompatActivity {

    Spinner mDropDownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vote);
        mDropDownMenu = (Spinner) findViewById(R.id.drop_down_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hashcode_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDropDownMenu.setAdapter(adapter);


    }
}
