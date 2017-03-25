package com.hq.demoviewpagerandtabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by My-PC on 3/25/2017.
 */

public class ChangepassActivity extends AppCompatActivity {
    private EditText oldpass, newpasswordchange, confnewpassword;
    private Button Passchange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        oldpass = (EditText) findViewById(R.id.oldpassword);
        newpasswordchange = (EditText) findViewById(R.id.newpasswordchange);
        confnewpassword = (EditText) findViewById(R.id.confnewpassword);
    }
}
