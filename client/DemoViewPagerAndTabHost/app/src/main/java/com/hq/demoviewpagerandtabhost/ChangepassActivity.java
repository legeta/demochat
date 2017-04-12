package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ChangepassActivity extends AppCompatActivity {
    private EditText oldpass, newpasswordchange, confnewpassword;
    private Button Passchange;
    private Socket mSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();

        Intent intent = this.getIntent();
        final String username = intent.getStringExtra("username");
        Log.d("hquserpasschange", username);

        oldpass = (EditText) findViewById(R.id.oldpassword);
        newpasswordchange = (EditText) findViewById(R.id.newpasswordchange);
        confnewpassword = (EditText) findViewById(R.id.confnewpassword);
        Passchange = (Button) findViewById(R.id.btnPasschange);

        Passchange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String old = oldpass.getText().toString();
                String newpass = newpasswordchange.getText().toString();
                String passconf = confnewpassword.getText().toString();
                Log.d("hquserpasschange", newpass);
                if (newpass.equals(passconf)) {
                    JSONObject data = null;
                    try {
                        data = new JSONObject("{\"username\": \"" + username + "\", \"oldpassword\": \"" + old + "\", \"newpassword\": \"" + newpass + "\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("hquserdk", String.valueOf(data));
                    mSocket.emit("UPDATE_PASS", data);


                    mSocket.on("SERVER_UPDATE_OK", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String updateok;
                                    updateok = args[0].toString();
                                    Toast.makeText(ChangepassActivity.this, "" + updateok, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChangepassActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            }
        });
    }
}
