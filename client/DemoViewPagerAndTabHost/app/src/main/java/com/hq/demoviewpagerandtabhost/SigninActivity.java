package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by My-PC on 3/3/2017.
 */

public class SigninActivity extends AppCompatActivity {
    private EditText edtusername;
    private EditText edtpassword;
    private Button signin;
    private Button signup;

    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        MSocket ms = new MSocket();
        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();
        mSocket.connect();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignin);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Sign in");
        toolbar.setSubtitle("Sign in");

        edtusername = (EditText) findViewById(R.id.txtUsername);
        edtpassword = (EditText) findViewById(R.id.txtPassword);
        signin = (Button) findViewById(R.id.btnSignin);
        signup = (Button) findViewById(R.id.btnSignup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                JSONObject data = null;
                try {
                    data = new JSONObject("{\"username\": \""+username+"\", \"password\": \""+password+"\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("hquser", String.valueOf(data));
                mSocket.emit("CLIENT_SIGN_IN", data);
                final ArrayList<String> mangUsernames = new ArrayList<String>();
                mSocket.on("SERVER_ACCEPT_USERNAME", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String usernamedk;
                                usernamedk = args[0].toString();
                                Toast.makeText(getApplicationContext(), "Your username: " + usernamedk, Toast.LENGTH_SHORT).show();
                                mSocket.on("LIST_ONLINE_USER", new Emitter.Listener() {
                                    @Override
                                    public void call(final Object... args) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                JSONObject data = (JSONObject) args[0];
                                                JSONArray manguser;
                                                try {
                                                    manguser = data.getJSONArray("danhsach");
                                                    for (int i = 0; i < manguser.length(); i++){
                                                        mangUsernames.add(manguser.get(i).toString());
                                                        Log.d("hqUser_mang",manguser.get(i).toString());
                                                    }
                                                }
                                                catch (JSONException e){
                                                    return;
                                                }

                                            }
                                        });
                                    }

                                });
                                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                intent.putExtra("username", edtusername.getText().toString());
                                intent.putExtra("manguseronline",mangUsernames);
                                startActivity(intent);
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
                                Toast.makeText(getApplicationContext(), " "+err, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });

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
