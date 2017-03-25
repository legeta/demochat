package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by My-PC on 3/3/2017.
 */

public class SigninActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button signin;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignin);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Sign in");
        toolbar.setSubtitle("Sign in");

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        signin = (Button) findViewById(R.id.btnSignin);
        signup = (Button) findViewById(R.id.btnSignup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent1);
            }
        });
    }


}
