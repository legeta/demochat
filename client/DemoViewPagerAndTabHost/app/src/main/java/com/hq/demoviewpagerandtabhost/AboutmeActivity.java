package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by My-PC on 3/25/2017.
 */

public class AboutmeActivity extends AppCompatActivity {
    private TextView myfname, mylname, myage, myphone, myusername, mygender;
    private Button btnchangeprofile, btnchangepass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Profile");
//        toolbar.setSubtitle("Sign up");

        myfname = (TextView) findViewById(R.id.myfname);
        mylname = (TextView) findViewById(R.id.mylname);
        myage = (TextView) findViewById(R.id.myage);
        myphone = (TextView) findViewById(R.id.myphone);
        myusername = (TextView) findViewById(R.id.myusername);
        mygender = (TextView) findViewById(R.id.mygender);
        btnchangepass = (Button) findViewById(R.id.btnchangepass);
        btnchangeprofile = (Button) findViewById(R.id.btnchangeprofile);

        btnchangeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutmeActivity.this, ChangeprofileActivity.class);
                startActivity(intent);
            }
        });
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutmeActivity.this, ChangepassActivity.class);
                startActivity(intent);
            }
        });
    }
}
