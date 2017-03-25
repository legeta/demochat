package com.hq.demoviewpagerandtabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by My-PC on 3/25/2017.
 */

public class AddActivity extends AppCompatActivity {
    private EditText edtfind;
    private Button btnfind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Friend");
//        toolbar.setSubtitle("Sign in");

        edtfind = (EditText) findViewById(R.id.edtfind);
        btnfind = (Button) findViewById(R.id.btnfind);
    }
}
