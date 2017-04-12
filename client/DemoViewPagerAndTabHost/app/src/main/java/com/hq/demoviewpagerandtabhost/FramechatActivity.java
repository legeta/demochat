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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by My-PC on 3/25/2017.
 */

public class FramechatActivity extends AppCompatActivity {
    private Socket mSocket;
    private TextView txtFramechat;
    private EditText edtChat;
    private Button btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatform);

        Intent intent = this.getIntent();
        final String username = intent.getStringExtra("chatto");

        MSocket ms = (MSocket) getApplication();
        mSocket = ms.getSocket();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChatform);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(username);
//        toolbar.setSubtitle("Sign in");

        txtFramechat = (TextView) findViewById(R.id.txtFramechat);
        edtChat = (EditText) findViewById(R.id.edtChat);
        btnSend = (Button) findViewById(R.id.btnSendChat);


        mSocket.on("RECEIVE_NEW_MESSAGE", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String usersend, message;
                        try {
                            message = data.getString("mess");
                            usersend = data.getString("usersend");
//
                            Log.d("hqusermess", data.getString("mess"));
                            Log.d("hqUsersend", data.getString("usersend"));
                        } catch (JSONException e) {
                            return;
                        }
                        txtFramechat.setText(txtFramechat.getText()+"\n"+message);
                    }
                });
            }

        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mechat = edtChat.getText().toString();
                JSONObject data = null;
                try {
                    data = new JSONObject("{\"des\": \""+username+"\", \"msg\": \""+mechat+"\"}");
                    Log.d("hqUser_sendmess",data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("CLIENT_SEND_MESSAGE", data);
                txtFramechat.setText(txtFramechat.getText()+"\n"+"Me: "+mechat);
                edtChat.setText("");
            }
        });

    }
}
