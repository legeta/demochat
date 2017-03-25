package com.hq.demoviewpagerandtabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My-PC on 3/25/2017.
 */

public class ChangeprofileActivity extends AppCompatActivity {
    private Spinner spnGenderchange;
    private EditText fnamechange, lnamechange, agechange, phonechange, usernamechange, passwordchange, passwordtoconf;
    private Button btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);

        spnGenderchange = (Spinner) findViewById(R.id.genderchange);
        fnamechange = (EditText) findViewById(R.id.fnamechange);
        lnamechange = (EditText) findViewById(R.id.lnamechange);
        agechange = (EditText) findViewById(R.id.agechange);
        phonechange = (EditText) findViewById(R.id.phonechange);
        usernamechange = (EditText) findViewById(R.id.newusernamechange);
        passwordchange = (EditText) findViewById(R.id.passwordtoconf);

        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnGenderchange.setAdapter(adapter);
        spnGenderchange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ChangeprofileActivity.this, spnGenderchange.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
