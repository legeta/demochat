package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by My-PC on 3/7/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private Spinner spnGender;
    private EditText fname, lname, edtage, edtphone, newusername, newpassword, confpassword;
    private Button btnSignup;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        MSocket ms = new MSocket();
        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();

        final ArrayList<String> mangUsernames = new ArrayList<String>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignup);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Sign in");
        toolbar.setSubtitle("Sign up");

        spnGender = (Spinner) findViewById(R.id.gender);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        edtage = (EditText) findViewById(R.id.age);
        edtphone = (EditText) findViewById(R.id.phone);
        newusername = (EditText) findViewById(R.id.newusername);
        newpassword = (EditText) findViewById(R.id.newpassword);
        confpassword = (EditText) findViewById(R.id.confpassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        final String[] gender = {null};


        final ArrayList<String> list = new ArrayList<String>();
        list.add("Male");
        list.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender[0] = String.valueOf(spnGender.getSelectedItem());
                Toast.makeText(SignupActivity.this, gender[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String age = edtage.getText().toString();
                final String phone = edtphone.getText().toString();
                final String username = newusername.getText().toString();
                final String password = newpassword.getText().toString();
                final String confirmpass = confpassword.getText().toString();

                if (password.equals(confirmpass)) {
                    JSONObject data = null;
                    try {
                        data = new JSONObject("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"phone\": \"" + phone + "\", \"firstname\": \"" + firstname + "\", \"lastname\": \"" + lastname + "\", \"age\": \"" + age + "\", \"gender\": \"" + gender[0] + "\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("hquserdk", String.valueOf(data));
                    mSocket.emit("CLIENT_SIGN_UP", data);

                    mSocket.on("SERVER_INSERT_OK", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject data = (JSONObject) args[0];
                                    try {
                                        data.getString("username");
//
                                        Log.d("hqUsername",data.getString("username"));
                                        Log.d("hqpassword",data.getString("password"));
                                    }
                                    catch (JSONException e){
                                        return;
                                    }
                                    Toast.makeText(SignupActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(SignupActivity.this, SigninActivity.class);
                                    startActivity(intent1);
                                }
                            });
                        }
                    });
                    mSocket.on("SERVER_SIGNUP_ERR", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String err;
                                    err = args[0].toString();
                                    Toast.makeText(SignupActivity.this, "Error: "+err, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(SignupActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(SignupActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
                else {
                    Toast.makeText(SignupActivity.this, "Password is not equals confirm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
