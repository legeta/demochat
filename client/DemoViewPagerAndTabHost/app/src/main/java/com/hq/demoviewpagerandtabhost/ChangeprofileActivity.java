package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by My-PC on 3/25/2017.
 */

public class ChangeprofileActivity extends AppCompatActivity {
    private Spinner spnGenderchange;
    private EditText fnamechange, lnamechange, agechange, phonechange, passwordtoconf;
    private Button btnProfilechange;
    private Socket mSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);

        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();

        Intent intent = this.getIntent();
        final String username = intent.getStringExtra("username");
        Log.d("hquserpasschange", username);

        spnGenderchange = (Spinner) findViewById(R.id.genderchange);
        fnamechange = (EditText) findViewById(R.id.fnamechange);
        lnamechange = (EditText) findViewById(R.id.lnamechange);
        agechange = (EditText) findViewById(R.id.agechange);
        phonechange = (EditText) findViewById(R.id.phonechange);
        passwordtoconf = (EditText) findViewById(R.id.passwordtoconf);
        btnProfilechange = (Button) findViewById(R.id.btnProfilechange);
        final String[] genderchange = {null};

        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnGenderchange.setAdapter(adapter);
        spnGenderchange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderchange[0] = String.valueOf(spnGenderchange.getSelectedItem());
                Toast.makeText(ChangeprofileActivity.this, spnGenderchange.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnProfilechange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String firstname = fnamechange.getText().toString();
                String lastname = lnamechange.getText().toString();
                String age = agechange.getText().toString();
                final String phone = phonechange.getText().toString();
                final String password = passwordtoconf.getText().toString();
                Log.d("hquserprofilechange", username);

                    JSONObject data = null;
                    try {
                        data = new JSONObject("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"firstname\": \"" + firstname + "\", \"lastname\": \"" + lastname + "\", \"age\": \"" + age + "\", \"gender\": \"" + genderchange[0] + "\", \"phone\": \"" + phone + "\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("hquserdk", String.valueOf(data));
                    mSocket.emit("UPDATE_PROFILE", data);


                    mSocket.on("SERVER_UPDATE_PROFILE", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String updateok;
                                    updateok = args[0].toString();
                                    Toast.makeText(ChangeprofileActivity.this, "" + updateok, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChangeprofileActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


            }
        });
    }
}
