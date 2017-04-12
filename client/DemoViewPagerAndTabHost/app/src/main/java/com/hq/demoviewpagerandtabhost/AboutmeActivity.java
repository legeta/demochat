package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by My-PC on 3/25/2017.
 */

public class AboutmeActivity extends AppCompatActivity {
    private TextView myfname, mylname, myage, myphone, myusername, mygender;
    private Button btnchangeprofile, btnchangepass;
    private Socket mSocket;
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

        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();

        Intent intent = this.getIntent();
        final String username = intent.getStringExtra("username");
        Log.d("hquserM2",username);

        mSocket.emit("CHECK_PROFILE", username);


        mSocket.on("SERVER_RETURN_PROFILE", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            myfname.setText(data.getString("firstname").toString());
                            mylname.setText(data.getString("lastname").toString());
                            myage.setText(data.getString("age").toString());
                            myphone.setText(data.getString("phone").toString());
                            mygender.setText(data.getString("gender").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(AboutmeActivity.this, "Profile" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnchangeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutmeActivity.this, ChangeprofileActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutmeActivity.this, ChangepassActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }
}
