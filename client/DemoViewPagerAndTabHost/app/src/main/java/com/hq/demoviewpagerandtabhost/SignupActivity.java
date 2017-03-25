package com.hq.demoviewpagerandtabhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 * Created by My-PC on 3/7/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private Spinner spnGender;
    private EditText fname, lname, age, phone, newusername, newpassword, confpassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignup);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Sign in");
        toolbar.setSubtitle("Sign up");

        spnGender = (Spinner) findViewById(R.id.gender);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        age = (EditText) findViewById(R.id.age);
        phone = (EditText) findViewById(R.id.phone);
        newusername = (EditText) findViewById(R.id.newusername);
        newpassword = (EditText) findViewById(R.id.newpassword);
        confpassword = (EditText) findViewById(R.id.confpassword);

        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnGender.setAdapter(adapter);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SignupActivity.this, spnGender.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
