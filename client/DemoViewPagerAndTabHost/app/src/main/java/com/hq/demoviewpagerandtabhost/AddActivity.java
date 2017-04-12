package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by My-PC on 3/25/2017.
 */

public class AddActivity extends AppCompatActivity {
    private EditText edtfind;
    private Button btnfind;
    private Socket mSocket;

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
//        String username2;
        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();

        Intent intent = this.getIntent();
        final String username1 = intent.getStringExtra("username");
        Log.d("hquser1", username1);

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username2 = edtfind.getText().toString();
                JSONObject data = null;
                try {
                    data = new JSONObject("{\"username1\": \"" + username1 + "\", \"username2\": \"" + username2 + "\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("hquserdk", String.valueOf(data));
                mSocket.emit("ADD_FRIENDS", data);


                mSocket.on("SERVER_INSERT_FRIENDS_OK", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject data = (JSONObject) args[0];
                                String friend;
                                try {
                                    data.getString("username1");
                                    friend = data.getString("username2");
//
                                    Log.d("hqUsername1", data.getString("username1"));
                                    Log.d("hqUsername2", data.getString("username2"));
                                } catch (JSONException e) {
                                    return;
                                }
                                Toast.makeText(AddActivity.this, "You and " + friend + " are friend now", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                mSocket.on("SERVER_ERR", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String err;
                                err = args[0].toString();
                                Toast.makeText(AddActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                mSocket.on("SERVER_RETURN_ERR", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String err;
                                err = args[0].toString();
                                Toast.makeText(AddActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
